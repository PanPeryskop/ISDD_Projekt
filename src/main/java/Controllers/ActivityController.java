package Controllers;

import Models.Activity;
import Models.ActivityDAO;
import Models.Client;
import Models.ClientDAO;
import Models.Trainer;
import Views.ActionDialog;
import Views.MainWindow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import java.util.List;

public class ActivityController {

    private final SessionFactory sessionFactory;
    private final MainWindow view;
    private final ActivityDAO activityDAO;
    private final ClientDAO clientDAO;

    public ActivityController(SessionFactory sessionFactory, MainWindow view) {
        this.sessionFactory = sessionFactory;
        this.view = view;
        this.activityDAO = new ActivityDAO();
        this.clientDAO = new ClientDAO();
    }

    public void showAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Activity> query = session.createQuery("FROM Activity", Activity.class);
            List<Activity> activities = query.getResultList();
            updateTable(activities);
            view.setViewName("Activities");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    private void updateTable(List<Activity> activities) {
        String[] columns = {"ID", "NAME", "DESCRIPTION", "PRICE", "DAY", "HOUR", "TRAINER"};
        Object[][] data = new Object[activities.size()][7];

        for (int i = 0; i < activities.size(); i++) {
            Activity a = activities.get(i);
            data[i][0] = a.getAId();
            data[i][1] = a.getAName();
            data[i][2] = a.getADescription();
            data[i][3] = a.getAPrice();
            data[i][4] = a.getADay();
            data[i][5] = a.getAHour();
            data[i][6] = a.getAtrainerInCharge() != null ? a.getAtrainerInCharge().getTName() : "N/A";
        }
        view.setTableData(columns, data);
    }

    public void loadEnrollmentData() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            
            List<Client> clients = clientDAO.getAllClients(session);
            view.getCmbEnrollClient().removeAllItems();
            view.getCmbEnrollClient().addItem("");
            for (Client c : clients) {
                view.getCmbEnrollClient().addItem(c.getMId() + " - " + c.getMName());
            }

            List<Activity> activities = session.createQuery("FROM Activity", Activity.class).getResultList();
            view.getCmbEnrollActivity().removeAllItems();
            view.getCmbEnrollActivity().addItem("");
            for (Activity a : activities) {
                view.getCmbEnrollActivity().addItem(a.getAId() + " - " + a.getAName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error loading enrollment data: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void enrollMemberFromUI() {
        String clientStr = (String) view.getCmbEnrollClient().getSelectedItem();
        String activityStr = (String) view.getCmbEnrollActivity().getSelectedItem();
        
        if (clientStr == null || activityStr == null || clientStr.isEmpty() || activityStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select both client and activity!");
            return;
        }

        String clientDNI = clientStr.split(" - ")[0].trim();
        String activityID = activityStr.split(" - ")[0].trim();

        Session session = null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();

            Client c = clientDAO.getClientById(session, clientDNI);
            
            if (c == null) {
                JOptionPane.showMessageDialog(view, "Client not found with DNI: " + clientDNI);
                return;
            }

            Activity activity = activityDAO.returnActivityByID(session, activityID);
            if (activity == null) {
                JOptionPane.showMessageDialog(view, "Activity not found: " + activityID);
                return;
            }

            Query<Long> checkQuery = session.createQuery(
                "SELECT COUNT(*) FROM Activity a JOIN a.clientSet c WHERE a.aId = :aid AND c.mNum = :mnum", Long.class);
            checkQuery.setParameter("aid", activityID);
            checkQuery.setParameter("mnum", c.getMNum());
            
            if (checkQuery.uniqueResult() > 0) {
                JOptionPane.showMessageDialog(view, "Client is already enrolled in this activity!");
                return;
            }

            session.createNativeQuery("INSERT INTO PERFORMS (p_id, p_num) VALUES (:aid, :mnum)")
                    .setParameter("aid", activityID)
                    .setParameter("mnum", c.getMNum())
                    .executeUpdate();

            tr.commit();
            JOptionPane.showMessageDialog(view, "Success! Member enrolled.");

        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            JOptionPane.showMessageDialog(view, "Enrollment Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void filterActivitiesUI() {
        String day = (String) view.getCmbFilterDay().getSelectedItem();
        String priceStr = view.getFilterPrice();

        if (day == null || day.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select a day!");
            return;
        }

        if (priceStr == null || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Enter a valid price!");
            return;
        }

        Session session = null;
        try {
            int price = Integer.parseInt(priceStr);
            session = sessionFactory.openSession();
            
            List<Object[]> results = activityDAO.getActivitiesByDayAndPrice(session, day, price);
            
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No activities found for " + day + " with price <= " + price);
                view.setTableData(new String[]{"NAME", "DAY", "PRICE"}, new Object[0][3]);
                return;
            }
            
            String[] columns = {"NAME", "DAY", "PRICE"};
            Object[][] data = new Object[results.size()][3];
            for (int i = 0; i < results.size(); i++) {
                data[i][0] = results.get(i)[0];
                data[i][1] = results.get(i)[1];
                data[i][2] = results.get(i)[2];
            }
            view.setTableData(columns, data);
            view.setViewName("Activities - Filtered by " + day + " (max price: " + price + ")");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Price must be a valid number!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Filter error: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void showStats() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            
            Long totalClients = session.createQuery("SELECT COUNT(*) FROM Client", Long.class).uniqueResult();
            Long totalTrainers = session.createQuery("SELECT COUNT(*) FROM Trainer", Long.class).uniqueResult();
            Long totalActivities = session.createQuery("SELECT COUNT(*) FROM Activity", Long.class).uniqueResult();
            
            Long totalEnrollments = session.createNativeQuery("SELECT COUNT(*) FROM PERFORMS", Long.class).uniqueResult();
            
            String stats = String.format(
                "=== STATISTICS ===\n\n" +
                "Total Clients: %d\n" +
                "Total Trainers: %d\n" +
                "Total Activities: %d\n" +
                "Total Enrollments: %d",
                totalClients, totalTrainers, totalActivities, totalEnrollments
            );
            
            JOptionPane.showMessageDialog(view, stats, "Statistics", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error getting stats: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void addNew() {
        ActionDialog dialog = new ActionDialog(view, true);
        dialog.setMode(ActionDialog.Mode.ADD);
        dialog.configureForActivity();
        dialog.setIdValue(generateNewId());

        Session session = sessionFactory.openSession();
        List<String> trainers = session.createQuery("SELECT t.tName FROM Trainer t", String.class).getResultList();
        dialog.setGenericComboModel(trainers.toArray(new String[0]));
        session.close();

        dialog.addConfirmListener(e -> {
            if (saveActivity(dialog, null)) {
                dialog.setConfirmed(true);
                dialog.dispose();
                showAll();
            }
        });

        dialog.addCancelListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void update(int selectedRow) {
        String aId = String.valueOf(view.getValueAt(selectedRow, 0));

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Activity activity = activityDAO.returnActivityByID(session, aId);

            if (activity == null) {
                JOptionPane.showMessageDialog(view, "Activity not found");
                return;
            }

            ActionDialog dialog = new ActionDialog(view, true);
            dialog.configureForActivity();
            dialog.setMode(ActionDialog.Mode.EDIT);

            List<String> trainers = session.createQuery("SELECT t.tName FROM Trainer t", String.class).getResultList();
            dialog.setGenericComboModel(trainers.toArray(new String[0]));

            dialog.setIdValue(activity.getAId());
            dialog.setNameValue(activity.getAName());
            dialog.setIdNrValue(String.valueOf(activity.getAPrice()));
            dialog.setPhoneValue(activity.getADay());
            dialog.setEmailValue(String.valueOf(activity.getAHour()));
            dialog.setCreationValue(activity.getADescription());
            
            if (activity.getAtrainerInCharge() != null) {
                dialog.setExtraValue(activity.getAtrainerInCharge().getTName());
            }

            final String activityId = aId;
            dialog.addConfirmListener(e -> {
                if (saveActivity(dialog, activityId)) {
                    dialog.setConfirmed(true);
                    dialog.dispose();
                    showAll();
                }
            });

            dialog.addCancelListener(e -> dialog.dispose());
            dialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void delete(int selectedRow) {
        String aId = String.valueOf(view.getValueAt(selectedRow, 0));

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Activity activity = activityDAO.returnActivityByID(session, aId);

            if (activity == null) {
                JOptionPane.showMessageDialog(view, "Activity not found");
                return;
            }

            ActionDialog dialog = new ActionDialog(view, true);
            dialog.configureForActivity();
            dialog.setMode(ActionDialog.Mode.DELETE);

            dialog.setIdValue(activity.getAId());
            dialog.setNameValue(activity.getAName());

            session.close();
            session = null;

            dialog.addConfirmListener(e -> {
                if (deleteActivity(aId)) {
                    dialog.dispose();
                    showAll();
                }
            });

            dialog.addCancelListener(e -> dialog.dispose());
            dialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    private String generateNewId() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Activity> query = session.createQuery("FROM Activity", Activity.class);
            int nextNum = query.getResultList().size() + 1;
            return String.format("AC%02d", nextNum);
        } catch (Exception e) {
            return "AC01";
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    private boolean saveActivity(ActionDialog dialog, String existingId) {
        Session session = null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();

            String aId = dialog.getIdValue();
            String aName = dialog.getNameValue();
            String priceStr = dialog.getIdNrValue();
            String aDay = dialog.getPhoneValue();
            String hourStr = dialog.getEmailValue();
            String aDescription = dialog.getCreationValue();
            String trainerName = dialog.getExtraValue();

            if (aName.isBlank() || aDescription.isBlank()) {
                JOptionPane.showMessageDialog(view, "Name and Description are required!");
                return false;
            }

            int price = 0;
            int hour = 0;
            try {
                price = priceStr.isBlank() ? 0 : Integer.parseInt(priceStr);
                hour = hourStr.isBlank() ? 0 : Integer.parseInt(hourStr);

                if (price < 0) {
                    JOptionPane.showMessageDialog(view, "Price must be a positive number!");
                    return false;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Price and Hour must be valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            Trainer trainer = null;
            if (!trainerName.isBlank()) {
                Query<Trainer> tQuery = session.createQuery("FROM Trainer t WHERE t.tName = :name", Trainer.class);
                tQuery.setParameter("name", trainerName);
                trainer = tQuery.uniqueResult();

                if (trainer != null) {
                    boolean checkConflict = true;
                    if (existingId != null) {
                        Activity existing = activityDAO.returnActivityByID(session, existingId);
                        if (existing != null && existing.getADay().equals(aDay) && 
                            existing.getAHour() == hour &&
                            existing.getAtrainerInCharge() != null &&
                            existing.getAtrainerInCharge().getTCod().equals(trainer.getTCod())) {
                            checkConflict = false;
                        }
                    }

                    if (checkConflict && activityDAO.checkTrainerConflict(session, trainer, aDay, hour)) {
                        JOptionPane.showMessageDialog(view, "Trainer is already busy at this time!");
                        return false;
                    }
                }
            }

            Activity activity;
            if (existingId != null) {
                activity = activityDAO.returnActivityByID(session, existingId);
                if (activity == null) {
                    JOptionPane.showMessageDialog(view, "Activity not found!");
                    return false;
                }
                activity.setAName(aName);
                activity.setADescription(aDescription);
                activity.setAPrice(price);
                activity.setADay(aDay);
                activity.setAHour(hour);
                activity.setAtrainerInCharge(trainer);
                session.merge(activity);
            } else {
                activity = new Activity(aId, aName, aDescription, price, aDay, hour);
                activity.setAtrainerInCharge(trainer);
                session.persist(activity);
            }

            tr.commit();
            return true;

        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            JOptionPane.showMessageDialog(view, "Error saving: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    private boolean deleteActivity(String aId) {
        Session session = null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();

            Activity activity = activityDAO.returnActivityByID(session, aId);

            if (activity != null) {
                session.remove(activity);
                tr.commit();
                JOptionPane.showMessageDialog(view, "Activity deleted successfully!");
                return true;
            }
            JOptionPane.showMessageDialog(view, "Activity not found!");
            return false;

        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            JOptionPane.showMessageDialog(view, "Error deleting: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}
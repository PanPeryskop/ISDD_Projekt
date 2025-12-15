package Controllers;

import Models.Activity;
import Models.ActivityDAO;
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

    public ActivityController(SessionFactory sessionFactory, MainWindow view) {
        this.sessionFactory = sessionFactory;
        this.view = view;
        this.activityDAO = new ActivityDAO();
    }

    public void showAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Activity> query = session.createQuery("FROM Activity", Activity.class);
            List<Activity> activities = query.getResultList();

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
                data[i][6] = a.getAtrainerInCharge() != null ? a.getAtrainerInCharge().getTCod() : "N/A";
            }

            view.setViewName("Activities");
            view.setTableData(columns, data);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void addNew() {
        ActionDialog dialog = new ActionDialog((java.awt.Frame) view, true);
        dialog.configureForActivity();
        dialog.setMode(ActionDialog.Mode.ADD);

        String newId = generateNewId();
        dialog.setIdValue(newId);

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

            ActionDialog dialog = new ActionDialog((java.awt.Frame) view, true);
            dialog.configureForActivity();
            dialog.setMode(ActionDialog.Mode.EDIT);

            dialog.setIdValue(activity.getAId());
            dialog.setNameValue(activity.getAName());
            dialog.setIdNrValue(String.valueOf(activity.getAPrice()));
            dialog.setPhoneValue(activity.getADay());
            dialog.setEmailValue(String.valueOf(activity.getAHour()));
            dialog.setCreationValue(activity.getADescription());
            dialog.setExtraValue(activity.getAtrainerInCharge() != null ? activity.getAtrainerInCharge().getTCod() : "");

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

            ActionDialog dialog = new ActionDialog((java.awt.Frame) view, true);
            dialog.configureForActivity();
            dialog.setMode(ActionDialog.Mode.DELETE);

            dialog.setIdValue(activity.getAId());
            dialog.setNameValue(activity.getAName());
            dialog.setIdNrValue(String.valueOf(activity.getAPrice()));
            dialog.setPhoneValue(activity.getADay());
            dialog.setEmailValue(String.valueOf(activity.getAHour()));
            dialog.setCreationValue(activity.getADescription());
            dialog.setExtraValue(activity.getAtrainerInCharge() != null ? activity.getAtrainerInCharge().getTCod() : "");

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
            return String.format("A%02d", nextNum);
        } catch (Exception e) {
            return "A001";
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
            String trainerCod = dialog.getExtraValue();

            if (aName.isBlank() || aDescription.isBlank()) {
                JOptionPane.showMessageDialog(view, "Name and Description are required!");
                return false;
            }

            int price = priceStr.isBlank() ? 0 : Integer.parseInt(priceStr);
            int hour = hourStr.isBlank() ? 0 : Integer.parseInt(hourStr);

            Trainer trainer = null;
            if (!trainerCod.isBlank()) {
                Query<Trainer> tQuery = session.createQuery("FROM Trainer t WHERE t.tCod = :cod", Trainer.class);
                tQuery.setParameter("cod", trainerCod);
                trainer = tQuery.uniqueResult();
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

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Price and Hour must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
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
            } else {
                JOptionPane.showMessageDialog(view, "Activity not found!");
                return false;
            }

        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            JOptionPane.showMessageDialog(view, "Error deleting: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}

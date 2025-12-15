package Controllers;

import Models.Trainer;
import Models.TrainerDAO;
import Views.ActionDialog;
import Views.MainWindow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrainerController {

    private final SessionFactory sessionFactory;
    private final MainWindow view;
    private final TrainerDAO trainerDAO;

    public TrainerController(SessionFactory sessionFactory, MainWindow view) {
        this.sessionFactory = sessionFactory;
        this.view = view;
        this.trainerDAO = new TrainerDAO();
    }

    public void showAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Trainer> query = session.createQuery("FROM Trainer", Trainer.class);
            List<Trainer> trainers = query.getResultList();

            String[] columns = {"COD", "NAME", "ID NUMBER", "PHONE", "EMAIL", "DATE", "NICK"};
            Object[][] data = new Object[trainers.size()][7];

            for (int i = 0; i < trainers.size(); i++) {
                Trainer t = trainers.get(i);
                data[i][0] = t.getTCod();
                data[i][1] = t.getTName();
                data[i][2] = t.getTidNumber();
                data[i][3] = t.getTphoneNumber();
                data[i][4] = t.getTEmail();
                data[i][5] = t.getTDate();
                data[i][6] = t.getTNick();
            }

            view.setViewName("Trainers");
            view.setTableData(columns, data);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void addNew() {
        ActionDialog dialog = new ActionDialog((java.awt.Frame) view, true);
        dialog.configureForTrainer();
        dialog.setMode(ActionDialog.Mode.ADD);
        
        String newId = generateNewId();
        dialog.setIdValue(newId);
        dialog.setCreationValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        dialog.addConfirmListener(e -> {
            if (saveTrainer(dialog, null)) {
                dialog.setConfirmed(true);
                dialog.dispose();
                showAll();
            }
        });
        
        dialog.addCancelListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void update(int selectedRow) {
        String tCod = String.valueOf(view.getValueAt(selectedRow, 0));
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Trainer> query = session.createQuery("FROM Trainer t WHERE t.tCod = :cod", Trainer.class);
            query.setParameter("cod", tCod);
            Trainer trainer = query.uniqueResult();
            
            if (trainer == null) {
                JOptionPane.showMessageDialog(view, "Trainer not found");
                return;
            }
            
            ActionDialog dialog = new ActionDialog((java.awt.Frame) view, true);
            dialog.configureForTrainer();
            dialog.setMode(ActionDialog.Mode.EDIT);
            
            dialog.setIdValue(trainer.getTCod());
            dialog.setNameValue(trainer.getTName());
            dialog.setIdNrValue(trainer.getTidNumber());
            dialog.setPhoneValue(trainer.getTphoneNumber() != null ? trainer.getTphoneNumber() : "");
            dialog.setEmailValue(trainer.getTEmail() != null ? trainer.getTEmail() : "");
            dialog.setCreationValue(trainer.getTDate());
            dialog.setExtraValue(trainer.getTNick() != null ? trainer.getTNick() : "");
            
            final String trainerId = tCod;
            dialog.addConfirmListener(e -> {
                if (saveTrainer(dialog, trainerId)) {
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
        String tCod = String.valueOf(view.getValueAt(selectedRow, 0));
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Trainer> query = session.createQuery("FROM Trainer t WHERE t.tCod = :cod", Trainer.class);
            query.setParameter("cod", tCod);
            Trainer trainer = query.uniqueResult();
            
            if (trainer == null) {
                JOptionPane.showMessageDialog(view, "Trainer not found");
                return;
            }
            
            ActionDialog dialog = new ActionDialog((java.awt.Frame) view, true);
            dialog.configureForTrainer();
            dialog.setMode(ActionDialog.Mode.DELETE);
            
            dialog.setIdValue(trainer.getTCod());
            dialog.setNameValue(trainer.getTName());
            dialog.setIdNrValue(trainer.getTidNumber());
            dialog.setPhoneValue(trainer.getTphoneNumber() != null ? trainer.getTphoneNumber() : "");
            dialog.setEmailValue(trainer.getTEmail() != null ? trainer.getTEmail() : "");
            dialog.setCreationValue(trainer.getTDate());
            dialog.setExtraValue(trainer.getTNick() != null ? trainer.getTNick() : "");
            
            session.close();
            session = null;
            
            dialog.addConfirmListener(e -> {
                if (deleteTrainer(tCod)) {
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
            Query<Trainer> query = session.createQuery("FROM Trainer", Trainer.class);
            int nextNum = query.getResultList().size() + 1;
            return String.format("M%03d", nextNum);
        } catch (Exception e) {
            return "M001";
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    private boolean saveTrainer(ActionDialog dialog, String existingId) {
        Session session = null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            
            String tCod = dialog.getIdValue();
            String tName = dialog.getNameValue();
            String tidNumber = dialog.getIdNrValue();
            String phone = dialog.getPhoneValue();
            String email = dialog.getEmailValue();
            String tDate = dialog.getCreationValue();
            String tNick = dialog.getExtraValue();
            
            if (tName.isBlank() || tidNumber.isBlank()) {
                JOptionPane.showMessageDialog(view, "Name and ID Number are required!");
                return false;
            }
            
            Trainer trainer;
            if (existingId != null) {
                Query<Trainer> query = session.createQuery("FROM Trainer t WHERE t.tCod = :cod", Trainer.class);
                query.setParameter("cod", existingId);
                trainer = query.uniqueResult();
                if (trainer == null) {
                    JOptionPane.showMessageDialog(view, "Trainer not found!");
                    return false;
                }
                trainer.setTName(tName);
                trainer.setTidNumber(tidNumber);
                trainer.setTphoneNumber(phone.isBlank() ? null : phone);
                trainer.setTEmail(email.isBlank() ? null : email);
                trainer.setTDate(tDate);
                trainer.setTNick(tNick.isBlank() ? null : tNick);
                session.merge(trainer);
            } else {
                trainer = new Trainer(tCod, tName, tidNumber, tDate);
                trainer.setTphoneNumber(phone.isBlank() ? null : phone);
                trainer.setTEmail(email.isBlank() ? null : email);
                trainer.setTNick(tNick.isBlank() ? null : tNick);
                session.persist(trainer);
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

    private boolean deleteTrainer(String tCod) {
        Session session = null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            
            Query<Trainer> query = session.createQuery("FROM Trainer t WHERE t.tCod = :cod", Trainer.class);
            query.setParameter("cod", tCod);
            Trainer trainer = query.uniqueResult();
            
            if (trainer != null) {
                session.remove(trainer);
                tr.commit();
                JOptionPane.showMessageDialog(view, "Trainer deleted successfully!");
                return true;
            } else {
                JOptionPane.showMessageDialog(view, "Trainer not found!");
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

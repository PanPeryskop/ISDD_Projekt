package Controllers;

import Models.Client;
import Models.ClientDAO;
import Views.ActionDialog;
import Views.MainWindow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientController {

    private final SessionFactory sessionFactory;
    private final MainWindow view;
    private final ClientDAO clientDAO;

    public ClientController(SessionFactory sessionFactory, MainWindow view) {
        this.sessionFactory = sessionFactory;
        this.view = view;
        this.clientDAO = new ClientDAO();
    }

    public void showAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<Client> clients = clientDAO.getAllClients(session);

            String[] columns = {"NUM", "NAME", "ID", "PHONE", "EMAIL", "START DATE", "CATEGORY"};
            Object[][] data = new Object[clients.size()][7];

            for (int i = 0; i < clients.size(); i++) {
                Client c = clients.get(i);
                data[i][0] = c.getMNum();
                data[i][1] = c.getMName();
                data[i][2] = c.getMId();
                data[i][3] = c.getMPhone();
                data[i][4] = c.getMemailMember();
                data[i][5] = c.getMstartingDateMember();
                data[i][6] = c.getMcategoryMember();
            }

            view.setViewName("Clients");
            view.setTableData(columns, data);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void addNew() {
        ActionDialog dialog = new ActionDialog((java.awt.Frame) view, true);
        configureDialogForClient(dialog);
        dialog.setMode(ActionDialog.Mode.ADD);
        
        String newId = generateNewId();
        dialog.setIdValue(newId);
        dialog.setCreationValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        dialog.addConfirmListener(e -> {
            if (saveClient(dialog, null)) {
                dialog.setConfirmed(true);
                dialog.dispose();
                showAll();
            }
        });
        
        dialog.addCancelListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void update(int selectedRow) {
        String mNum = String.valueOf(view.getValueAt(selectedRow, 0));
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Client client = clientDAO.returnClientByMemberNumber(session, mNum);
            
            if (client == null) {
                JOptionPane.showMessageDialog(view, "Client not found");
                return;
            }
            
            ActionDialog dialog = new ActionDialog((java.awt.Frame) view, true);
            configureDialogForClient(dialog);
            dialog.setMode(ActionDialog.Mode.EDIT);
            
            dialog.setIdValue(client.getMNum());
            dialog.setNameValue(client.getMName());
            dialog.setIdNrValue(client.getMId());
            dialog.setPhoneValue(client.getMPhone() != null ? client.getMPhone() : "");
            dialog.setEmailValue(client.getMemailMember() != null ? client.getMemailMember() : "");
            dialog.setCreationValue(client.getMstartingDateMember());
            dialog.setExtraValue(client.getMcategoryMember() != null ? client.getMcategoryMember().toString() : "");
            
            final String clientId = mNum;
            dialog.addConfirmListener(e -> {
                if (saveClient(dialog, clientId)) {
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
        String mNum = String.valueOf(view.getValueAt(selectedRow, 0));
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Client client = clientDAO.returnClientByMemberNumber(session, mNum);
            
            if (client == null) {
                JOptionPane.showMessageDialog(view, "Client not found");
                return;
            }
            
            ActionDialog dialog = new ActionDialog((java.awt.Frame) view, true);
            configureDialogForClient(dialog);
            dialog.setMode(ActionDialog.Mode.DELETE);
            
            dialog.setIdValue(client.getMNum());
            dialog.setNameValue(client.getMName());
            dialog.setIdNrValue(client.getMId());
            dialog.setPhoneValue(client.getMPhone() != null ? client.getMPhone() : "");
            dialog.setEmailValue(client.getMemailMember() != null ? client.getMemailMember() : "");
            dialog.setCreationValue(client.getMstartingDateMember());
            dialog.setExtraValue(client.getMcategoryMember() != null ? client.getMcategoryMember().toString() : "");
            
            session.close();
            session = null;
            
            dialog.addConfirmListener(e -> {
                if (deleteClient(mNum)) {
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

    private void configureDialogForClient(ActionDialog dialog) {
        dialog.configureForClient();
    }

    private String generateNewId() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<Client> clients = clientDAO.getAllClients(session);
            int nextNum = clients.size() + 1;
            return String.format("S%03d", nextNum);
        } catch (Exception e) {
            return "S001";
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    private boolean saveClient(ActionDialog dialog, String existingId) {
        Session session = null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            
            String mNum = dialog.getIdValue();
            String mName = dialog.getNameValue();
            String mId = dialog.getIdNrValue();
            String phone = dialog.getPhoneValue();
            String email = dialog.getEmailValue();
            String startDate = dialog.getCreationValue();
            String categoryStr = dialog.getExtraValue();

            
            if (mName.isBlank() || mId.isBlank() || startDate.isBlank()) {
                JOptionPane.showMessageDialog(view, "Name, DNI and Birth Date are required!");
                return false;
            }

            if (!Utils.Validator.isValidId(mId)) {
                JOptionPane.showMessageDialog(view, "Invalid DNI format! Must be 8 digits + 1 Uppercase Letter.");
                return false;
            }

            if (!email.isBlank() && !Utils.Validator.isValidEmail(email)) {
                JOptionPane.showMessageDialog(view, "Invalid Email format (xxx@xxx.xx)!");
                return false;
            }

            if (!phone.isBlank() && !Utils.Validator.isValidPhone(phone)) {
                JOptionPane.showMessageDialog(view, "Invalid Phone format!");
                return false;
            }
            
            if (!Utils.Validator.isValidDate(startDate)) {
                JOptionPane.showMessageDialog(view, "Client must be at least 18 years old and date format must be yyyy-MM-dd!");
                return false;
            }
            
            char category = categoryStr.isBlank() ? 'A' : categoryStr.toUpperCase().charAt(0);
            
            Client client;
            if (existingId != null) {
                client = clientDAO.returnClientByMemberNumber(session, existingId);
                if (client == null) {
                    JOptionPane.showMessageDialog(view, "Client not found!");
                    return false;
                }
                client.setMName(mName);
                client.setMId(mId);
                client.setMPhone(phone.isBlank() ? null : phone);
                client.setMemailMember(email.isBlank() ? null : email);
                client.setMstartingDateMember(startDate);
                client.setMcategoryMember(category);
                session.merge(client);
            } else {
                if (clientDAO.existMemberNumber(session, mNum)) {
                    JOptionPane.showMessageDialog(view, "Member number already exists!");
                    return false;
                }
                if (clientDAO.existDNI(session, mId)) {
                    JOptionPane.showMessageDialog(view, "DNI already exists!");
                    return false;
                }
                client = new Client(mNum, mName, mId, startDate, category);
                client.setMPhone(phone.isBlank() ? null : phone);
                client.setMemailMember(email.isBlank() ? null : email);
                clientDAO.insertClient(session, client);
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

    private boolean deleteClient(String mNum) {
        Session session = null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            
            Client client = clientDAO.returnClientByMemberNumber(session, mNum);
            if (client != null) {
                clientDAO.deleteClient(session, client);
                tr.commit();
                JOptionPane.showMessageDialog(view, "Client deleted successfully!");
                return true;
            } else {
                JOptionPane.showMessageDialog(view, "Client not found!");
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
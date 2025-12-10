package Controllers;

import Config.HibernateUtil;
import Models.Activity;
import Models.Client;
import Models.Trainer;
import Views.MainWindow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainController implements ActionListener {

    private final SessionFactory sessionFactory;
    private final MainWindow view;

    public MainController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.view = new MainWindow();
        
        addListeners();
        
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HibernateUtil.close();
                System.exit(0);
            }
        });
        
        showInit();
        view.setVisible(true);
    }

    private void addListeners() {
        view.addClientMenuListener(this);
        view.addTrainerMenuListener(this);
        view.addActivitiesMenuListener(this);
        view.addInitMenuListener(this);
    }

        
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "ShowClients" -> showClients();
            case "ShowTrainers" -> showTrainers();
            case "ShowActivities" -> showActivities();
            case "ShowInit" -> showInit();
        }
    }

    private void showInit() {
        view.setViewName("Welcome to ISDD Project");
        view.setTableData(new String[]{"Info"}, new Object[][]{{"Select an option from the menu above"}});
    }

    private void showClients() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Client> query = session.createQuery("FROM Client", Client.class);
            List<Client> clients = query.getResultList();

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

    private void showTrainers() {
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

    private void showActivities() {
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
                data[i][6] = a.getAtrainerInCharge() != null ? a.getAtrainerInCharge().getTName() : "N/A";
            }

            view.setViewName("Activities");
            view.setTableData(columns, data);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}
package Controllers;

import Config.HibernateUtil;
import Views.MainWindow;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class MainController implements ActionListener {

    private final SessionFactory sessionFactory;
    private final MainWindow view;
    
    private final ClientController clientController;
    private final TrainerController trainerController;
    private final ActivityController activityController;
    
    private String currentView = "Init";

    public MainController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.view = new MainWindow();
        
        this.clientController = new ClientController(sessionFactory, view);
        this.trainerController = new TrainerController(sessionFactory, view);
        this.activityController = new ActivityController(sessionFactory, view);
        
        addListeners();
        
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HibernateUtil.close();
                System.exit(0);
            }
        });
        
        showInit();
        view.setViewMode("Init");
        view.setVisible(true);
    }

    private void addListeners() {
        view.addClientMenuListener(this);
        view.addTrainerMenuListener(this);
        view.addActivitiesMenuListener(this);
        view.addInitMenuListener(this);
        
        view.addNewButtonListener(this);
        view.addDeleteButtonListener(this);
        view.addUpdateButtonListener(this);
        
        view.addApplyFilterListener(this);
        view.addJoinButtonListener(this);
        view.addStatsListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "ShowClients" -> {
                currentView = "Clients";
                view.setViewMode("Clients");
                clientController.showAll();
            }
            case "ShowTrainers" -> {
                currentView = "Trainers";
                view.setViewMode("Trainers");
                trainerController.showAll();
            }
            case "ShowActivities" -> {
                currentView = "Activities";
                view.setViewMode("Activities");
                activityController.showAll();
                activityController.loadEnrollmentData();
            }
            case "ShowInit" -> {
                currentView = "Init";
                view.setViewMode("Init");
                showInit();
            }
            case "New" -> handleNew();
            case "Delete" -> handleDelete();
            case "Update" -> handleUpdate();
            case "ApplyFilter" -> {
                if ("Activities".equals(currentView)) {
                    activityController.filterActivitiesUI();
                }
            }
            case "JoinSquad" -> activityController.enrollMemberFromUI();
            case "ShowStats" -> activityController.showStats();
        }
    }

    private void showInit() {
        view.setViewName("Welcome to ISDD Project");
        view.setTableData(new String[]{"Info"}, new Object[][]{{"Select an option from the menu above"}});
        view.setViewMode("Init");
    }


    private void handleNew() {
        switch (currentView) {
            case "Clients" -> clientController.addNew();
            case "Trainers" -> trainerController.addNew();
            case "Activities" -> activityController.addNew();
            default -> JOptionPane.showMessageDialog(view, "Please select a view first (Clients, Trainers, or Activities)");
        }
    }

    private void handleDelete() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Please select a row to delete");
            return;
        }
        
        switch (currentView) {
            case "Clients" -> clientController.delete(selectedRow);
            case "Trainers" -> trainerController.delete(selectedRow);
            case "Activities" -> activityController.delete(selectedRow);
            default -> JOptionPane.showMessageDialog(view, "Please select a view first");
        }
    }

    private void handleUpdate() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Please select a row to update");
            return;
        }
        
        switch (currentView) {
            case "Clients" -> clientController.update(selectedRow);
            case "Trainers" -> trainerController.update(selectedRow);
            case "Activities" -> activityController.update(selectedRow);
            default -> JOptionPane.showMessageDialog(view, "Please select a view first");
        }
    }
}
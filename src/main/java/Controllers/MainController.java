package Controllers;

import org.hibernate.SessionFactory;
import Views.MessageView;
import java.util.Scanner;

public class MainController {

    private final SessionFactory sessionFactory;
    private MessageView msgView = null;

    public MainController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        msgView = new MessageView();
        menu();
    }

    private void menu() {
        Scanner keyboard = new Scanner(System.in);
        String option = "";

        do {
            msgView.menu();
            option = keyboard.nextLine();

            switch (option) {
                case "1":
                    new ClientController(sessionFactory);
                    //insertClient()
                    break;
                case "2":
                    new TrainerController(sessionFactory);
                    //ActivitiesByTrainerID
                    break;
                case "3":
                    new ActivityController(sessionFactory);
                    //ActivitiesByClientNumber
                    break;
            }
        } while (!option.equals("4"));
    }
}
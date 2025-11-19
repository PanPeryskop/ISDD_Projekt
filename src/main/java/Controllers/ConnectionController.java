package Controllers;

import Config.HibernateUtil;
import org.hibernate.SessionFactory;

public class ConnectionController {

    public ConnectionController() {
        SessionFactory sessionFactory = DBConnect();
        if (sessionFactory == null) {
            System.err.println("connection failed, unable to connect");
            System.exit(1);
        }

        new MainController(sessionFactory);

    }

    public static SessionFactory DBConnect() {
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            System.out.println("Connected sucesfully");
            return sessionFactory;
        } catch (ExceptionInInitializerError e) {
            var c = e.getCause();
            System.err.println("Connectio error. check .cfg.xml: " + (c != null ? c.getMessage() : "Unknown cause"));
            return null;
        }
    }

}
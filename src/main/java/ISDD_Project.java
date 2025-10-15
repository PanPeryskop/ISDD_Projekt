import Config.HibernateUtil;
import Models.Client;

import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ISDD_Project {

    public static void main(String[] args) {
        System.out.println("START");
        SessionFactory sessionFactory = DBConnect();
        Session sesion = null;
        Transaction tr = null;

        try (Scanner keyboard = new Scanner(System.in)) {
            String option = "";

            do {
                menu();
                option = keyboard.nextLine();

                switch (option) {
                    case "1":
                        sesion = sessionFactory.openSession();
                        tr = sesion.beginTransaction();
                        try {
                            Query<Client> q = sesion.createQuery("FROM Client c", Client.class);
                            List<Client> clients = q.getResultList();

                            System.out.println("====================================================================================================================");
                            System.out.println("NUM"+ "\t" + "NAME"+ "\t" + "ID"+ "\t" + "BIRTHDATE"+ "\t" + "PHONE"+ "\t" + "EMAIL"+ "\t" + "START DATE"+ "\t" + "CATEGORY");
                            System.out.println("====================================================================================================================");

                            for (Client c : clients) {
                                System.out.println(c.getMNum() + "\t" + c.getMName() + "\t" + c.getMId() + "\t"
                                        + c.getMBirthdate() + "\t" + c.getMPhone() + "\t"
                                        + c.getMemailMember() + "\t" + c.getMstartingDateMember() + "\t"
                                        + c.getMcategoryMember());
                            }

                            tr.commit();
                        } catch (Exception e) {
                            if (tr != null) tr.rollback();
                            System.out.print("Error");
                        } finally {
                            if (sesion != null && sesion.isOpen()) {
                                sesion.close();
                            }
                        }
                        break;

                    case "2":
                        sesion = sessionFactory.openSession();
                        tr = sesion.beginTransaction();
                        try {
                            Query<Client> p = sesion.createNativeQuery("SELECT * FROM CLIENT", Client.class);
                            List<Client> clients2 = p.getResultList();
                            for (Client c : clients2) {
                                System.out.println(c.getMName());
                            }
                            tr.commit();
                        } catch (Exception e) {
                            if (tr != null) {
                                tr.rollback();
                            }
                            System.err.println("Error: " + e.getMessage());
                        } finally {
                            if (sesion != null) {
                                sesion.close();
                            }
                        }
                        break;


                }
            } while (!option.equals("0"));
        } catch (HibernateException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static SessionFactory DBConnect() {
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            System.err.println("Connected");

        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            System.err.println("Error" + (cause != null ? cause.getMessage() : e.getMessage()));

        }
        return sessionFactory;
    }

    public static void menu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Pokaż wszystkich członków (HQL)");
        System.out.println("2. Pokaż wszystkich członków (SQL)");
        System.out.println("3. Pokaż wszystkich członków (Named Query)");
        System.out.println("4. Imię i telefon wszystkich członków");
        System.out.println("5. Imię i kategoria członków z wybranej kategorii");
        System.out.println("6. Imię monitora po nicku");
        System.out.println("7. Informacje o członku po imieniu");
        System.out.println("8. Aktywności wg dnia i opłaty");
        System.out.println("9. Członkowie wg kategorii (HQL Named Query)");
        System.out.println("10. Członkowie wg kategorii (SQL Named Query)");
        System.out.println("0. Wyjście");
    }
}

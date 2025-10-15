import Config.HibernateUtil;
import Models.Client;

import java.util.List;
import java.util.Objects;
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
        Session session = null;
        Transaction tr = null;

        try (Scanner keyboard = new Scanner(System.in)) {
            String option = "";

            do {
                menu();
                option = keyboard.nextLine();

                switch (option) {
                    case "1":
                        session = sessionFactory.openSession();
                        tr = session.beginTransaction();
                        try {
                            Query<Client> q = session.createQuery("FROM Client c", Client.class);
                            List<Client> clients = q.getResultList();

                            System.out.println("====================================================================================================================");
                            System.out.println("NUM" + "\t" + "NAME" + "\t" + "ID" + "\t" + "BIRTHDATE" + "\t" + "PHONE" + "\t" + "EMAIL" + "\t" + "START DATE" + "\t" + "CATEGORY");
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
                            if (session != null && session.isOpen()) {
                                session.close();
                            }
                        }
                        break;

                    case "2":
                        session = sessionFactory.openSession();
                        tr = session.beginTransaction();
                        try {
                            Query<Client> p = session.createNativeQuery("SELECT * FROM CLIENT", Client.class);
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
                            if (session.isOpen()) {
                                session.close();
                            }
                        }
                        break;

                    case "3":
                        session = sessionFactory.openSession();
                        tr = session.beginTransaction();
                        try {
                            Query<Client> p = session.createNamedQuery("Client.findAll", Client.class);
                            List<Client> clients3 = p.getResultList();
                            for (Client c : clients3) {
                                System.out.println(c.getMName());
                            }
                            tr.commit();
                        } catch (Exception e) {
                            if (tr != null) {
                                tr.rollback();
                            }
                        } finally {
                            if (session.isOpen()) {
                                session.close();
                            }
                        }
                        break;

                    case "4":
                        session = sessionFactory.openSession();
                        tr = session.beginTransaction();
                        try {
                            Query<Object[]> q = session.createQuery("SELECT Client.mName, Client.mPhone FROM Client", Object[].class);
                            List<Object[]> rows = q.getResultList();
                            for (Object[] row : rows) {
                                System.out.println(row[0] + " " + row[1]);
                            }
                            tr.commit();
                        } catch (Exception e) {
                            if (tr != null) tr.rollback();
                        } finally {
                            if (session.isOpen()) session.close();
                        }
                        break;

                    case "5":
                        session = sessionFactory.openSession();
                        tr = session.beginTransaction();
                        try {
                            System.out.println("Podaj kategorię: A, B, C ,D lub E");
                            String category = keyboard.nextLine().toUpperCase();
                            Query<Object[]> q = session.createQuery("SELECT c.mName, c.mcategoryMember FROM Client c WHERE c.mcategoryMember = :category", Object[].class);
                            q.setParameter("category", category.charAt(0));
                            List<Object[]> rows = q.getResultList();
                            for (Object[] row : rows) System.out.println(row[0] + " " + row[1]);
                            tr.commit();
                        } catch (Exception e) {
                            if (tr != null) tr.rollback();
                        } finally {
                            if (session.isOpen()) session.close();
                        }
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
        System.out.println("1. Show all members (HQL)");
        System.out.println("2. Show all members (SQL)");
        System.out.println("3. Show all members (Named Query)");
        System.out.println("4. Name and phone of all members");
        System.out.println("5. Name and category of members from selected category");
        System.out.println("6. Monitor's name by nickname");
        System.out.println("7. Member information by name");
        System.out.println("8. Activities by day and payment");
        System.out.println("9. Members by category (HQL Named Query)");
        System.out.println("10. Members by category (SQL Named Query)");
        System.out.println("0. Exit");
    }
}

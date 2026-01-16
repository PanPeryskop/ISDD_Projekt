package Models;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class ActivityDAO {

    public ActivityDAO() {
    }

    public boolean existActivityID(Session session, String activityId) {
        try {
            Query<Activity> q = session.createQuery("SELECT a FROM Activity a WHERE a.aId = :idValue", Activity.class);
            q.setParameter("idValue", activityId);
            return q.getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean checkTrainerConflict(Session session, Trainer trainer, String day, int hour) {
        try {
            Query<Long> query = session.createQuery(
                "SELECT count(a) FROM Activity a WHERE a.atrainerInCharge = :trainer AND a.aDay = :day AND a.aHour = :hour", Long.class);
            query.setParameter("trainer", trainer);
            query.setParameter("day", day);
            query.setParameter("hour", hour);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public Activity returnActivityByID(Session session, String activityId) {
        try {
            Query<Activity> q = session.createQuery("SELECT a FROM Activity a WHERE a.aId = :idValue", Activity.class);
            q.setParameter("idValue", activityId);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> getActivitiesByTrainer(Session session, Trainer trainer) {
        Query<Object[]> query = session.createQuery(
            "SELECT a.aName, a.aDay, a.aPrice FROM Activity a WHERE a.atrainerInCharge = :trainer",
            Object[].class);
        query.setParameter("trainer", trainer);
        return query.getResultList();
    }

    public List<Object[]> getActivitiesByClient(Session session, Client client) {
        Query<Object[]> query = session.createQuery(
            "SELECT a.aName, a.aDay, a.aPrice FROM Activity a JOIN a.clientSet c WHERE c = :client",
            Object[].class);
        query.setParameter("client", client);
        return query.getResultList();
    }

    public List<Object[]> getActivitiesByDayAndPrice(Session session, String day, int price) {
        Query<Object[]> query = session.createQuery(
            "SELECT a.aName, a.aDay, a.aPrice FROM Activity a WHERE a.aDay = :day AND a.aPrice <= :price",
            Object[].class);
        query.setParameter("day", day);
        query.setParameter("price", price);
        return query.getResultList();
    }

    public Activity getActivityByName(Session session, String name) {
        try {
            Query<Activity> query = session.createQuery(
                "FROM Activity a WHERE a.aName = :name", Activity.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> getMembersByActivity(Session session, Activity activity) {
        Query<Object[]> query = session.createQuery(
            "SELECT c.mName, c.memailMember FROM Activity a JOIN a.clientSet c WHERE a = :activity",
            Object[].class);
        query.setParameter("activity", activity);
        return query.getResultList();
    }

    public String executeStatsProcedure(Session session) {
        try {
            Query<Object> query = session.createNativeQuery("CALL GetStatistics()", Object.class);
            List<Object> result = query.getResultList();
            if (result.isEmpty()) return "No stats available.";
            return result.get(0).toString();
        } catch (Exception e) {
            return "Error executing stats: " + e.getMessage();
        }
    }
}
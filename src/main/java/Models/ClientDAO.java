package Models;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class ClientDAO {

    public ClientDAO() {
    }

    public boolean existMemberNumber(Session session, String memberNum) {
        try {
            Query<Client> q = session.createQuery("SELECT c FROM Client c WHERE c.mNum = :mNumValue", Client.class);
            q.setParameter("mNumValue", memberNum);
            return q.getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existDNI(Session session, String dni) {
        try {
            Query<Client> q = session.createQuery("SELECT c FROM Client c WHERE c.mId = :idValue", Client.class);
            q.setParameter("idValue", dni);
            return q.getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    public void insertClient(Session session, Client client) throws Exception {
        session.persist(client);
    }

    public Client returnClientByMemberNumber(Session session, String memberNum) {
        try {
            Query<Client> q = session.createQuery("SELECT c FROM Client c WHERE c.mNum = :mNumValue", Client.class);
            q.setParameter("mNumValue", memberNum);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Client> getAllClients(Session session) {
        Query<Client> query = session.createQuery("FROM Client", Client.class);
        return query.getResultList();
    }

    public void deleteClient(Session session, Client client) {
        session.remove(client);
    }

    public List<Object[]> getClientsByCategory(Session session, char category) {
        Query<Object[]> query = session.createQuery(
            "SELECT c.mName, c.mcategoryMember FROM Client c WHERE c.mcategoryMember = :cat", Object[].class);
        query.setParameter("cat", category);
        return query.getResultList();
    }

    public List<Object[]> getNameAndPhone(Session session) {
        Query<Object[]> query = session.createQuery(
            "SELECT c.mName, c.mPhone FROM Client c", Object[].class);
        return query.getResultList();
    }

    public Client getClientByName(Session session, String name) {
        try {
            Query<Client> query = session.createQuery("FROM Client c WHERE c.mName = :name", Client.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Client> getClientsByCategoryNamed(Session session, char category) {
        Query<Client> query = session.createNamedQuery("Client.findByMcategoryMember", Client.class);
        query.setParameter("mcategoryMember", category);
        return query.getResultList();
    }

    public Client getClientById(Session session, String id) {
        try {
            Query<Client> query = session.createQuery("FROM Client c WHERE c.mId = :id", Client.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
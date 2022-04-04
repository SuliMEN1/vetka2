package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL primary key AUTO_INCREMENT, name CHAR(30),"
                + " lastName CHAR (30), age TINYINT UNSIGNED)";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS users";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
        session.close();
        System.out.printf("User с именем – %s добавлен в базу данных%n", name);

    }

    @Override
    public void removeUserById(long id) {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE from User WHERE id = :id");
        query.setParameter("id", id);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public List<User> getAllUsers() {
        session = Util.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Query query = session.createQuery(criteriaQuery);
        List<User> list =  query.getResultList();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE  FROM User");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}

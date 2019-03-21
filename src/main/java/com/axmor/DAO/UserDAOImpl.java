package com.axmor.DAO;

import com.axmor.Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public User getByLogin(String login) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = (User) session.createQuery("FROM User WHERE name = :name").setParameter("name", login).getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void merge(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
    }
}

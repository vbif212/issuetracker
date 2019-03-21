package com.axmor.DAO;

import com.axmor.Models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;

public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public User getByLogin(String login) throws HibernateException, NoResultException {
        try (Session session = sessionFactory.openSession()) {
            return (User) session.createQuery("FROM User WHERE name = :name").setParameter("name", login).getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException();
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void merge(User user) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }
}

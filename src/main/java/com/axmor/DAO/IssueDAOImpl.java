package com.axmor.DAO;

import com.axmor.Models.Issue;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;
import java.util.List;

public class IssueDAOImpl implements IssueDAO {

    SessionFactory sessionFactory;

    public IssueDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Issue> getAll() throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Issue ").list();
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Issue getByID(int id) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Issue.class, id);
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void delete(Issue issue) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(issue);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void merge(Issue issue) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(issue);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }
}

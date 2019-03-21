package com.axmor.DAO;

import com.axmor.Models.Issue;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class IssueDAOImpl implements IssueDAO {

    SessionFactory sessionFactory;

    public IssueDAOImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Issue> getAll() {
        List<Issue> issues;
        try (Session session = sessionFactory.openSession()) {
            issues = session.createQuery("FROM Issue ").list();
        } catch (Exception e) {
            return null;
        }
        return issues;
    }

    @Override
    public Issue getByID(int id) {
        Issue issue = null;
        try (Session session = sessionFactory.openSession()) {
            issue = session.get(Issue.class, id);
        } catch (Exception e) {
            return null;
        }
        return issue;
    }

    @Override
    public void delete(Issue issue) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(issue);
            session.getTransaction().commit();
        }
    }

    @Override
    public void merge(Issue issue) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(issue);
            session.getTransaction().commit();
        }
    }
}

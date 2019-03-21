package com.axmor.DAO;

import com.axmor.Models.Comment;
import com.axmor.Models.Issue;
import com.axmor.Models.Status;
import com.axmor.Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IssueDAOImplTest {

    private SessionFactory sessionFactory;
    private Session session = null;
    private IssueDAO issueDAO;

    @Before
    public void setUp() throws Exception {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Issue.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Comment.class);
        configuration.setProperty("dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("connection.url", "jdbc:h2:mem:test");
        configuration.setProperty("hbm2ddl.auto", "create");
        configuration.setProperty("show_sql", "true");
        sessionFactory = configuration.configure().buildSessionFactory();
        issueDAO = new IssueDAOImpl(sessionFactory);
    }

    @After
    public void tearDown() throws Exception {
        sessionFactory.close();
    }

    @Test
    public void getAll() {
        session = sessionFactory.openSession();
        Issue i1 = new Issue("i1", "desc");
        Issue i2 = new Issue("i2", "desc");
        session.beginTransaction();
        session.save(i1);
        session.save(i2);
        session.getTransaction().commit();
        session.close();
        List<Issue> issueList = issueDAO.getAll();
        assertNotNull(issueList);
        assertEquals(2, issueList.size());
    }

    @Test
    public void getByID() {
        session = sessionFactory.openSession();
        Issue i1 = new Issue("i3", "desc");
        session.beginTransaction();
        session.save(i1);
        session.getTransaction().commit();
        session.close();
        Issue i2 = issueDAO.getByID(i1.getId());
        assertNotNull(i2);
        assertEquals(i1, i2);
    }

    @Test
    public void delete() {
        session = sessionFactory.openSession();
        Issue i1 = new Issue("i5", "desc");
        session.beginTransaction();
        session.save(i1);
        session.getTransaction().commit();
        session.close();
        issueDAO.delete(i1);
        session = sessionFactory.openSession();
        session.beginTransaction();
        Issue i2 = session.get(Issue.class, i1.getId());
        session.close();
        assertNull(i2);
    }

    @Test
    public void merge() {
        session = sessionFactory.openSession();
        Issue i1 = new Issue("i4", "desc");
        session.beginTransaction();
        session.save(i1);
        session.getTransaction().commit();
        session.close();
        i1.setStatus(Status.Open);
        issueDAO.merge(i1);
        session = sessionFactory.openSession();
        session.beginTransaction();
        Issue i2 = session.get(Issue.class, i1.getId());
        session.close();
        assertNotNull(i2);
        assertEquals(i1.getStatus(), i2.getStatus());
    }
}
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

import static org.junit.Assert.*;

public class UserDAOImplTest {

    private SessionFactory sessionFactory;
    private Session session = null;
    private UserDAO userDAO;

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
        userDAO = new UserDAOImpl(sessionFactory);
    }

    @After
    public void tearDown() throws Exception {
        sessionFactory.close();
    }

    @Test
    public void add() {
        User user = new User("user", "password");
        userDAO.add(user);
        session = sessionFactory.openSession();
        User user1 = session.get(User.class, user.getId());
        session.close();
        assertNotNull(user1);
        assertEquals(user, user1);
    }

    @Test
    public void getByLogin() {
        session = sessionFactory.openSession();
        User user = new User("user1", "password");
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        User user1 = userDAO.getByLogin(user.getName());
        assertNotNull(user1);
        assertEquals(user, user1);
    }

    @Test
    public void merge() {
        session = sessionFactory.openSession();
        User user = new User("user2", "password");
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        user.setPassword("newPassword");
        userDAO.merge(user);
        session = sessionFactory.openSession();
        session.beginTransaction();
        User user1 = session.get(User.class, user.getId());
        session.close();
        assertNotNull(user1);
        assertEquals(user.getPassword(), user1.getPassword());
    }
}
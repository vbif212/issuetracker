package com.axmor;


import com.axmor.Util.Filters;
import com.axmor.Controllers.IndexController;
import com.axmor.Controllers.IssueController;
import com.axmor.Controllers.LoginController;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static spark.Spark.*;

/**
 * Simple Issue Tracker made with Java Spark
 *
 * @author  Mikhail Sotnikov
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        port(80);
        staticFiles.location("/public");

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Filters filters = new Filters();
        IndexController indexController = new IndexController();
        LoginController loginController = new LoginController(sessionFactory);
        IssueController issueController = new IssueController(sessionFactory);

        before("/issues", filters.getAuthorization());
        before("/issues/*", filters.getAuthorization());
        before("/logout", filters.getAuthorization());

        get("/", indexController.getServerIndexPage());

        post("/login", loginController.getHandleLoginPost());
        post("/logout", loginController.getHandleLogoutPost());

        get("/issues", issueController.getHandleIssuesGet());
        get("/issues/new", issueController.getHandleIssuesNewGet());
        get("/issues/:id", issueController.getHandleIssuesIDGet());
        post("/issues", issueController.getHandleIssuesPost());
        post("/issues/:id/comment", issueController.getHandleIssuesIDCommentPost());
        delete("/issues/:id", issueController.getHandleIssuesIDDelete());
    }
}
package com.axmor.Controllers;

import com.axmor.Models.User;
import com.axmor.DAO.UserDAO;
import com.axmor.DAO.UserDAOImpl;
import org.apache.commons.text.StringEscapeUtils;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.persistence.NoResultException;

/**
 * Controller for handle login/logout request.
 *
 * @author  Mikhail Sotnikov
 */
public class LoginController {

    private SessionFactory sessionFactory;

    /**
     * LoginController constructor
     *
     * @param sessionFactory Hibernate.SessionFactory
     */
    public LoginController(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    private Route handleLoginPost = (Request request, Response response) -> {
        UserDAO userDAO = new UserDAOImpl(sessionFactory);
        String escapedLogin = StringEscapeUtils.escapeHtml4(request.queryParams("username"));
        String escapedPassword = StringEscapeUtils.escapeHtml4(request.queryParams("password"));
        try {
            User user = userDAO.getByLogin(escapedLogin);
            if (!user.getPassword().equals(escapedPassword)) {
                response.status(401);
                return  "";
            }
        } catch (NoResultException e) {
            userDAO.add(new User(escapedLogin, escapedPassword));
        }
        request.session(true).attribute("login", escapedLogin);
        return "/issues";
    };

    /**
     * Get login spark.Route
     *
     * @return spark.Route for handle login post request.
     */
    public Route getHandleLoginPost() {
        return handleLoginPost;
    }

    private Route handleLogoutPost = (Request request, Response response) -> {
        request.session().invalidate();
        response.redirect("/");
        return null;
    };

    /**
     * Get logout spark.Route
     *
     * @return spark.Route for handle logout post request.
     */
    public Route getHandleLogoutPost() {
        return handleLogoutPost;
    }
}

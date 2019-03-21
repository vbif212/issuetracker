package com.axmor.Controllers;

import com.axmor.DAO.IssueDAO;
import com.axmor.DAO.IssueDAOImpl;
import com.axmor.Models.Comment;
import com.axmor.Models.Issue;
import com.axmor.Models.Status;
import com.axmor.Models.User;
import com.axmor.DAO.UserDAO;
import com.axmor.DAO.UserDAOImpl;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.mustache.MustacheTemplateEngine;

import org.apache.commons.text.StringEscapeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for handle request for Issues.
 *
 * @author  Mikhail Sotnikov
 */
public class IssueController {

    private IssueDAO issueDAO ;
    private UserDAO userDAO;
    private MustacheTemplateEngine mustacheTemplateEngine;

    /**
     * IssueController constructor
     *
     * @param sessionFactory Hibernate.SessionFactory
     */
    public IssueController(SessionFactory sessionFactory){
        issueDAO = new IssueDAOImpl(sessionFactory);
        userDAO = new UserDAOImpl(sessionFactory);
        mustacheTemplateEngine = new MustacheTemplateEngine();
    }

    private Route handleIssuesNewGet = (Request request, Response response) -> {
        Map<String, Object> map = new HashMap<>();
        String login = request.session().attribute("login");
        map.put("login", login);
        return render(map, "newissue.mustache");
    };

    private Route handleIssuesGet = (Request request, Response response) -> {
        List<Issue> list = issueDAO.getAll();
        Map<String, Object> map = new HashMap<>();
        if (list.isEmpty()) {
            map.put("empty", "Create the first issue!");
        } else {
            map.put("issues", list);
        }
        return render(map, "issues.mustache");
    };

    private Route handleIssuesPost = (Request request, Response response) -> {
        String escapedName = StringEscapeUtils.escapeHtml4(request.queryParams("name"));
        String escapedDescription = StringEscapeUtils.escapeHtml4(request.queryParams("description"));
        User user = userDAO.getByLogin(request.session().attribute("login"));
        Issue issue = new Issue(escapedName, escapedDescription);
        user.getIssues().add(issue);
        issue.setUser(user);
        try {
            userDAO.merge(user);
        } catch (ConstraintViolationException e) {
            response.status(409);
        }
        return "";
    };

    private Route handleIssuesIDGet = (Request request, Response response) -> {
        try {
            int id = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.params(":id")));
            Issue issue = issueDAO.getByID(id);
            if (issue == null) {
                response.status(404);
            } else {
                String login = request.session().attribute("login");
                Map<String, Object> map = new HashMap<>();
                if (login.equals(issue.getUser().getName())) {
                    map.put("disabled", "");
                } else {
                    map.put("disabled", "disabled");
                }
                map.put("issue", issue);
                map.put("user", login);
                return render(map, "issue.mustache");
            }
        } catch (NumberFormatException e) {
            response.status(400);
        }
        return "";
    };

    private Route handleIssuesIDDelete = (Request request, Response response) -> {
        try {
            int id = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.params(":id")));
            Issue issue = issueDAO.getByID(id);
            if (issue == null) {
                response.status(404);
            } else {
                String login = request.session().attribute("login");
                if (login.equals(issue.getUser().getName())) {
                    issueDAO.delete(issue);
                } else {
                    response.status(403);
                }
            }
        } catch (NumberFormatException e) {
            response.status(400);
        }
        return "";
    };

    private Route handleIssuesIDCommentPost = (Request request, Response response) -> {
        try {
            int id = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.params(":id")));
            Issue issue = issueDAO.getByID(id);
            if (issue == null) {
                response.status(404);
            } else {
                User user = userDAO.getByLogin(request.session().attribute("login"));
                Status escapedStatus = Status.valueOf(StringEscapeUtils.escapeHtml4(request.queryParams("status")));
                Comment comment = new Comment(escapedStatus, StringEscapeUtils.escapeHtml4(request.queryParams("comment")));
                user.getComments().add(comment);
                issue.getComments().add(comment);
                comment.setIssue(issue);
                comment.setUser(user);
                issue.setStatus(escapedStatus);
                userDAO.merge(user);
                issueDAO.merge(issue);
                response.redirect("/issues/" + id);
            }
        } catch (NumberFormatException e) {
            response.status(400);
        }
        return "";
    };

    /**
     * Render and return HTML-page for new Issue in the form of spark.Route
     *
     * @return spark.Route for handle issuesnew get request.
     */
    public Route getHandleIssuesNewGet() {
        return handleIssuesNewGet;
    }

    /**
     * Render and return HTML-page for List of issues in the form of spark.Route
     *
     * @return spark.Route for handle issues get request.
     */
    public Route getHandleIssuesGet() {
        return handleIssuesGet;
    }

    /**
     * Create new issue.
     *
     * @return spark.Route for handle issues post request.
     */
    public Route getHandleIssuesPost() {
        return handleIssuesPost;
    }

    /**
     * Render and return HTML-page for one Issues in the form of spark.Route
     *
     * @return spark.Route for handle issuesid get request.
     */
    public Route getHandleIssuesIDGet() {
        return handleIssuesIDGet;
    }

    /**
     * Delete issue by id.
     *
     * @return spark.Route for handle issuesid delete request.
     */
    public Route getHandleIssuesIDDelete() {
        return handleIssuesIDDelete;
    }

    /**
     * Create new comment of issue.
     *
     * @return spark.Route for handle issuesidcomment post request.
     */
    public Route getHandleIssuesIDCommentPost() {
        return handleIssuesIDCommentPost;
    }

    /**
     * Render and return HTML-page
     *
     * @param model Map where key is name of value on "Mustache" HTML-page
     * @param path path to the Mustache template.
     * @return HTML rendering page in the form of a String
     */
    private String render(Map<String, Object> model, String path) {
        return mustacheTemplateEngine.render(new ModelAndView(model, path));
    }
}

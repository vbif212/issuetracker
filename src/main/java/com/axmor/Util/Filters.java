package com.axmor.Util;

import spark.Filter;
import spark.Request;
import spark.Response;


/**
 * Filers for check authorization.
 *
 * @author  Mikhail Sotnikov
 */
public class Filters {

    private Filter authorization = (Request req, Response res) -> {
        if (req.session().attribute("login") == null) {
            res.status(401);
            res.redirect("/");
        }
    };

    public Filter getAuthorization() {
        return authorization;
    }
}

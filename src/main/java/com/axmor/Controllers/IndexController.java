package com.axmor.Controllers;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Controller for return main html page.
 *
 * @author  Mikhail Sotnikov
 */
public class IndexController {
    private Route serverIndexPage = (Request request, Response response) -> {
        response.redirect("index.html");
        return null;
    };

    public Route getServerIndexPage() {
        return serverIndexPage;
    }
}

package com.axmor;

import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.port;

/**
 * Application entry point
 */
public class Main {
    public static void main(String[] args) {
        port(80);
        get("/", (Request req, Response res) ->
                "<html><body><h1>Hello, world!</h1></body></html>");
    }
}

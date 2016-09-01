package de.oo2.tank.server;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

/**
 * This class adds the routes for the website.
 */
public class WebsiteRoutes {

    public WebsiteRoutes() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Hello World!");

        // The hello.jade template file is in the resources/templates directory
        get("/hello", (rq, rs) -> new ModelAndView(map, "hello"), new JadeTemplateEngine());
    }
}

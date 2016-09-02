package de.oo2.tank.server.route;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

/**
 * This class adds the route for the website.
 */
public class WebsiteRoutes {

    public WebsiteRoutes() {

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(configuration.getClass(), "/public/");

        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "app.html");

        }, new FreeMarkerEngine(configuration));

    }

}

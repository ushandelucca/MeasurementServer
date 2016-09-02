package de.oo2.tank.server.route;

import de.oo2.tank.server.model.Tank;
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
    private Tank tankModel = null;
    private Map<String, Object> model = null;

    /**
     * Constructor.
     *
     * @param tank the tankModel model
     */
    public WebsiteRoutes(Tank tank) {
        this.tankModel = tank;

        // Freemarker configuration
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(configuration.getClass(), "/public/");

        get("/", (request, response) -> {

            return new ModelAndView(getModel(), "app.html");

        }, new FreeMarkerEngine(configuration));

    }

    /**
     * Returns the model to be mapped in the view by freemarker.
     *
     * @return the model
     */
    private Map<String, Object> getModel() {
        if (model == null) {
            model = initialiseModel();
        }
        model = updateModel(model);
        return model;
    }

    /**
     * Initialises the model.
     *
     * @return the model
     */
    private Map<String, Object> initialiseModel() {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("version", tankModel.getVersion());
        attributes.put("hostname", tankModel.getHostname());

        return attributes;
    }

    /**
     * Updates the values in the model.
     *
     * @param model the model to be updated
     * @return the updated model
     */
    private Map<String, Object> updateModel(Map<String, Object> model) {
        return model;
    }
}

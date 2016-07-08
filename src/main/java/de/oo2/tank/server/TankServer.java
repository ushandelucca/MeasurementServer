package de.oo2.tank.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main application class.
 */
public class TankServer {
    private static final Logger logger = LoggerFactory.getLogger(TankServer.class.getName());

    /**
     * main() Method as the application entry point
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws Exception {

        TankServer app = new TankServer();

        app.startServer();
    }

    /**
     * a better solution could be:
     *
     * - http://www.pippo.ro/
     * - http://intercoolerjs.org
     * - https://github.com/jakerella/jquery-mockjax
     */


    /**
     * This method deploys the services and applications and starts the server.
     */
    public void startServer() {

        // Configurator configurator = Configurator;
        Configurator configurator = new Configurator();

        Server server = new Server(Integer.parseInt(configurator.getServerPort()));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // deploying the REST Service for the water tank
        // http://localhost:8080/webapi/water/temperatures
        // http://raspberrypi.local:8080/webapi/water/temperatures
        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/webapi/*");
        jerseyServlet.setInitOrder(1);
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "de.oo2.tank.server");
        jerseyServlet.setInitParameter("de.oo2a.database.name", configurator.getDatabaseName());
        jerseyServlet.setInitParameter("de.oo2a.database.user", configurator.getDatabaseUser());
        jerseyServlet.setInitParameter("de.oo2a.database.password", configurator.getDatabasePassword());


        // deploying the web client
        // http://localhost:8080/index.html
        ServletHolder staticServlet = context.addServlet(DefaultServlet.class, "/*");
        staticServlet.setInitParameter("resourceBase", configurator.getResourceBase());
        staticServlet.setInitParameter("pathInfoOnly", "true");

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            logger.error("Error while starting jetty", t);
        }
    }

    /**
     * This method creates the database.
     */
    private void createDatabase() {
    }

}
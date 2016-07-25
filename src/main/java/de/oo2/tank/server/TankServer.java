package de.oo2.tank.server;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

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

        logger.info("Starting the server.");

        Swarm swarm = new Swarm(args);

        JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
        archive.addAsResource("logback.xml");

        archive.addPackages(true, "de/oo2/tank");
        archive.addAllDependencies();

        swarm.start().deploy( archive );
    }

    /**
     * a better solution could be:
     *
     * - http://intercoolerjs.org
     * - https://github.com/jakerella/jquery-mockjax
     */

}
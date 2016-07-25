package de.oo2.tank.server;

import de.oo2.tank.model.Measurement;
import de.oo2.tank.server.dao.MeasurementDao;
import de.oo2.tank.server.dao.MeasurementDataAccessException;
import de.oo2.tank.server.dao.MeasurementQueryComposer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import static de.oo2.tank.server.Configurator.KEY_DATABASE_NAME;

/**
 *

 Choose between PUT and POST based on idempotence of the action:
 PUT implies putting a resource - completely replacing whatever is available at the given URL with a different thing.
 By definition, a PUT is idempotent. Do it as many times as you like, and the result is the same. x=5 is idempotent.
 You can PUT a resource whether it previously exists, or not (eg, to Create, or to Update)!
 POST updates a resource, adds a subsidiary resource, or causes a change. A POST is not idempotent, in the way that x++ is not idempotent.
 */


@Path("/water")
public class WaterRessource {
    private static final Logger logger = LoggerFactory.getLogger(WaterRessource.class.getName());

    private MeasurementDao dao = null;

    @Context
    private Application app;

    @POST
    @Path("/temperatures")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Measurement createMeasurement(Measurement measurement) throws Exception {
        Measurement createdMeasurement = dao.createMeasurement(measurement);
        return createdMeasurement;
    }

    @GET
    @Path("/temperature/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Returns a measurement
     * http://localhost:8180/webapi/water/temperature?id=1234
     */
    public Measurement getTemperature(@PathParam("id") String identifier) throws Exception {
        Measurement measurement = dao.readMeasurementById(identifier);

        return measurement;
    }

    @GET
    @Path("/temperatures")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Select the temperatures in the db using the query.
     * http://localhost:8180/webapi/water/temperatures?query=return&begin=2014-01-13&end=2014-01-20&sort=-date&max_result=10
     * <pre>
     *     Query parameters
     *     - return      : trigger a query and return the result
     *     - begin       : begin of the measurement period
     *     - end         : end of the measurement period
     *     - sort        : sort the result where +date means date ascending, -date descending
     *     - max_result  : select the maximum number of measurements. If this parameter ist
     *                     not set, then the maximum number of measurements is set to 101
     * </pre>
     */
    public Measurement[] getTemperatures(@Context UriInfo uriInfo) throws Exception {

        MeasurementQueryComposer queryComposer = new MeasurementQueryComposer(uriInfo.getQueryParameters());
        String query = queryComposer.getQuery();
        String sort = queryComposer.getSort();
        int limit = queryComposer.getLimit();

        Measurement[] measurements = dao.readMeasurementsWithQuery(query, sort, limit);

        if (measurements.length < 1) {
            return new Measurement[0];
        }

        return measurements;
    }

    /**
     * Returns the Data Access Object.
     *
     * @return the DAO
     */
    private MeasurementDao getMeasurementDao() {

        if (dao == null) {
            String dbNamne = (String) app.getProperties().getOrDefault(KEY_DATABASE_NAME, "test");
            dao = new MeasurementDao(dbNamne);
        }

       return dao;
    }

    /**
     * Convenience method for the exception handling.
     *
     * @param message   the exception message
     * @param throwable the original exception
     * @throws MeasurementDataAccessException
     */
    private void handleException(String message, Throwable throwable) throws MeasurementDataAccessException {
        logger.error(message, throwable);
        throw new MeasurementDataAccessException(message, throwable);
    }

}
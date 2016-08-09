package de.oo2.tank.server;

import de.oo2.tank.server.dao.MeasurementDao;
import de.oo2.tank.server.dao.PersistenceException;

/**
 * This class provides the functionality for the management of the temperature measurements.
 */
public class TemperatureService {

    // the data access
    private MeasurementDao dao = null;

    /**
     * Constructor for the service.
     *
     * @param config the configuration
     */
    public TemperatureService(Configurator config) {
        this.dao = new MeasurementDao(config.getDbName(), config.getDbHost(), config.getDbPort());
    }

    /**
     * Constructor for the service.
     *
     * @param dao the data access object
     */
    public TemperatureService(MeasurementDao dao) {
        this.dao = dao;
    }

    /**
     * Save a temperature measurement to the database.
     *
     * @param measurement the measurement
     * @return the measuremt saved in the database
     * @throws PersistenceException
     */
    public Measurement saveTemperatue(Measurement measurement) throws PersistenceException {
        Measurement createdMeasurement = dao.createMeasurement(measurement);
        return createdMeasurement;
    }

    /**
     * Read a temperature measurement from the database.
     *
     * @param id of temperature measurement
     * @return the temperature measurement
     * @throws PersistenceException
     */
    public Measurement readTemperature(String id) throws PersistenceException {
        Measurement measurement = dao.readMeasurementById(id);

        return measurement;
    }

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
     * @param queryParameters the query parameters
     * @return the queried measurements
     * @throws PersistenceException
     */
    public Measurement[] selectTemperatures(String queryParameters) throws PersistenceException {

        Measurement[] measurements = dao.readMeasurementsWithQuery(queryParameters);

        return measurements;
    }

}

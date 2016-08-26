package de.oo2.tank.server;

import de.oo2.tank.server.model.Measurement;
import de.oo2.tank.server.model.ModelException;
import de.oo2.tank.server.persistence.MongoDao;
import de.oo2.tank.server.persistence.PersistenceException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

/**
 * This class provides the functionality for the management of the measurements.
 */
public class MeasurementService {

    // the data access
    private MongoDao dao = null;

    /**
     * Constructor for the service.
     *
     * @param config the configuration
     */
    public MeasurementService(Configurator config) {
        this.dao = new MongoDao(config.getDbName(), config.getDbHost(), config.getDbPort());
    }

    /**
     * Constructor for the service.
     *
     * @param dao the data access object
     */
    public MeasurementService(MongoDao dao) {
        this.dao = dao;
    }

    /**
     * Save a measurement to the database.
     *
     * @param measurement the measurement
     * @return the measurement saved in the database
     * @throws PersistenceException in case of failure
     * @throws ModelException in case of failure
     */
    public Measurement saveMeasurement(Measurement measurement) throws PersistenceException, ModelException {
        validate(measurement);
        Measurement createdMeasurement = dao.createMeasurement(measurement);
        return createdMeasurement;
    }

    /**
     * Read a measurement from the database.
     *
     * @param id of temperature measurement
     * @return the temperature measurement
     * @throws PersistenceException in case of failure
     */
    public Measurement readMeasurement(String id) throws PersistenceException {
        Measurement measurement = dao.readMeasurementById(id);

        return measurement;
    }

    /**
     * Select the measurements in the db using the query.
     * <pre>
     *     Query parameters
     *     - return      : trigger a query and return the result
     *     - begin       : begin of the measurement period
     *     - end         : end of the measurement period
     *     - sort        : sort the result where +date means date ascending, -date descending
     *     - max_result  : select the maximum number of measurements. If this parameter ist
     *                     not set, then the maximum number of measurements is set to 101
     * </pre>
     *
     * @param queryParameters the query parameters
     * @return the queried measurements
     * @throws PersistenceException in case of failure
     */
    public Measurement[] selectMeasurements(String queryParameters) throws PersistenceException {

        Measurement[] measurements = dao.readMeasurementsWithQuery(queryParameters);

        return measurements;
    }

    /**
     * Returns the result of the measurement validation.
     *
     * @param measurement the measurement
     * @return true when the measurement kis valid
     * @throws ModelException in case of failure
     */
    private boolean validate(Measurement measurement) throws ModelException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);

        if (violations.isEmpty()) {
            return true;
        } else if (violations.size() == 1) {
            throw new ModelException("Error during validation of the measurement: " + violations.iterator().next().getMessage());
        } else if (violations.size() > 1) {
            StringBuilder message = new StringBuilder("Multiple Errors during validation of the measurement: ");

            Iterator<ConstraintViolation<Measurement>> iter = violations.iterator();

            while (iter.hasNext()) {
                message.append(iter.next().getMessage());

                if (iter.hasNext()) {
                    message.append(", ");
                }
            }

            throw new ModelException(message.toString());
        }

        return true;
    }

}

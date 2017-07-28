package de.oo2.tank.server.measurement;

import de.oo2.tank.server.ServerConfiguration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class provides the functionality for the management of the measurements.
 */
public class MeasurementController {

    // the data access
    private MeasurementDao dao = null;

    /**
     * Constructor for the service.
     *
     * @param config the configuration
     */
    public MeasurementController(ServerConfiguration config) {
        this.dao = new MeasurementDao(config.getDbName(), config.getDbHost(), config.getDbPort());
    }

    /**
     * Constructor for the service.
     *
     * @param dao the data access object
     */
    public MeasurementController(MeasurementDao dao) {
        this.dao = dao;
    }

    /**
     * Save a measurement to the database.
     *
     * @param measurement the measurement
     * @return the measurement saved in the database
     * @throws MeasurementDaoException in case of failure
     * @throws MeasurementException in case of failure
     */
    public Measurement saveMeasurement(Measurement measurement) throws MeasurementDaoException, MeasurementException {
        validate(measurement);
        Measurement createdMeasurement = dao.createMeasurement(measurement);
        return createdMeasurement;
    }

    /**
     * Read a measurement from the database.
     *
     * @param id of the measurement
     * @return the the measurement
     * @throws MeasurementDaoException in case of failure
     */
    public Measurement readMeasurement(String id) throws MeasurementDaoException {
        Measurement measurement = dao.readMeasurementWithId(id);

        return measurement;
    }

    /**
     * Select the measurements in the db using the query.
     * <pre>
     *     Query parameters
     *     - return      : trigger a query and return the result
     *     - sensor      : sensor name
     *     - begin       : begin of the measurement period
     *     - end         : end of the measurement period
     *     - sort        : sort the result where +date means date ascending, -date descending
     *     - max_result  : select the maximum number of measurements. If this parameter ist
     *                     not set, then the maximum number of measurements is set to 101
     * </pre>
     *
     * @param queryParameters the query parameters
     * @return the queried measurements
     * @throws MeasurementDaoException in case of failure
     */
    public Measurement[] selectMeasurements(Map<String, String[]> queryParameters) throws MeasurementDaoException {

        Measurement[] measurements = dao.readMeasurementsWithQuery(queryParameters);

        return measurements;
    }

    /**
     * Update a existing measurement in the database.
     *
     * @param measurement the measurement
     * @return the measurement saved in the database
     * @throws MeasurementDaoException in case of failure
     * @throws MeasurementException in case of failure
     */
    public Measurement updateMeasurement(Measurement measurement) throws MeasurementDaoException, MeasurementException {

        // validate id
        if ((measurement.getId() == null) || ("".equals(measurement.getId()))) {
            throw new MeasurementException("ID must not be empty");
        }

        validate(measurement);
        Measurement updatedMeasurement = dao.updateMeasurement(measurement);
        return updatedMeasurement;
    }

    /**
     * Delete a measurement from the database.
     *
     * @param id of the measurement
     * @throws MeasurementDaoException in case of failure
     */
    public void deleteMeasurement(String id) throws MeasurementDaoException {
        dao.deleteMeasurement(id);
    }

    /**
     * Returns the result of the measurement validation.
     *
     * @param measurement the measurement
     * @return true when the measurement kis valid
     * @throws MeasurementException in case of failure
     */
    private boolean validate(Measurement measurement) throws MeasurementException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);

        if (violations.isEmpty()) {
            return true;
        } else if (violations.size() == 1) {
            throw new MeasurementException("Error during validation of the measurement: " + violations.iterator().next().getMessage());
        } else if (violations.size() > 1) {
            StringBuilder message = new StringBuilder("Multiple Errors during validation of the measurement: ");

            Iterator<ConstraintViolation<Measurement>> iter = violations.iterator();

            while (iter.hasNext()) {
                message.append(iter.next().getMessage());

                if (iter.hasNext()) {
                    message.append(", ");
                }
            }

            throw new MeasurementException(message.toString());
        }

        return true;
    }

}

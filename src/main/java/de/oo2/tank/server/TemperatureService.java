package de.oo2.tank.server;

import de.oo2.tank.model.Measurement;
import de.oo2.tank.server.dao.MeasurementDao;

/**
 * Created by Peter on 29.07.2016.
 */
public class TemperatureService {

    private MeasurementDao dao = null;

    public Measurement createMeasurement(Measurement measurement) throws Exception {
        Measurement createdMeasurement = getMeasurementDao().createMeasurement(measurement);
        return createdMeasurement;
    }

    /**
     * Returns a measurement
     */
    public Measurement getTemperature(String id) throws Exception {
        Measurement measurement = getMeasurementDao().readMeasurementById(id);

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
     */
    public Measurement[] getTemperatures(String queryParameters) throws Exception {

        // MeasurementQueryComposer queryComposer = new MeasurementQueryComposer(queryParameters);
        String query = ""; // queryComposer.getQuery();
        String sort = ""; // queryComposer.getSort();
        int limit = 0; // queryComposer.getLimit();

        Measurement[] measurements = getMeasurementDao().readMeasurementsWithQuery(query, sort, limit);

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
            // String dbNamne = (String) app.getProperties().getOrDefault(KEY_DATABASE_NAME, "test");
            dao = new MeasurementDao("test", "docker.local", 21017);
        }

        return dao;

    }
}

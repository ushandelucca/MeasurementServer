package de.oo2.tank.server.dao;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import de.oo2.tank.server.Configurator;
import de.oo2.tank.server.Measurement;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.jongo.Oid.withOid;

/**
 * Data Access Object for the measurements.
 */
public class MeasurementDao {
    private static final Logger logger = LoggerFactory.getLogger(MeasurementDao.class.getName());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    /**
     * Returns the database.
     *
     * @return the db
     */
    private DB getDatabase() {
        MongoClient mongoClient = null;

        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            logger.error("Error while connecting to the database.", e);
        }

        String dataBaseName = new Configurator().getDatabaseName();

        return mongoClient.getDB(dataBaseName);
    }

    /**
     * Returns the DB connection.
     *
     * @return the DB connection
     */
    protected MongoCollection getMeasurements() {
        DB db = getDatabase();

        Jongo jongo = new Jongo(db);
        MongoCollection measurements = jongo.getCollection("measurements");

        return measurements;
    }

    /**
     * Creates a new entry in the db and stores the given measurement date in the new row. The returned
     * <code>Measurement</code> object contains the unique id (primary key) of the measurement.
     *
     * @param measurement data to store
     * @return the stored measurement
     * @throws MeasurementDataAccessException
     */
    public Measurement createMeasurement(Measurement measurement) throws MeasurementDataAccessException {
        MongoCollection measurements = getMeasurements();

        measurements.save(measurement);

        return measurement;
    }

    /**
     * Read a measurement by a given id.
     *
     * @param id of the measurement
     * @return the measurement if the measurement is not found in the db the return value is <code>null</code>.
     * @throws MeasurementDataAccessException
     */
    public Measurement readMeasurementById(String id) throws MeasurementDataAccessException {
        MongoCollection measurements = getMeasurements();

        Measurement m = measurements.findOne(withOid(id)).as(Measurement.class);

        return m;
    }

    /**
     * Read a measurement by a given begin and end of a time period.
     *
     * @param query the query
     * @return An array of the matching <code>Measurement</code> objects. If no <code>Measurement</code> matches
     * the period an empty array will be returned.
     * @throws MeasurementDataAccessException
     */
    public Measurement[] readMeasurementsWithQuery(String query) throws MeasurementDataAccessException {
        MongoCollection measurements = getMeasurements();
        MongoCursor<Measurement> all = null;

        // TODO
        // http://jongo.org/#querying
        // integrate the MeasurementQueryComposer in this method

        try {
            all = measurements.find(query).as(Measurement.class);
        }
        catch (Exception e) {
            handleException("Error while selecting the measurements!", e);
        }

        List<Measurement> myList = Lists.newArrayList(all.iterator());

        return myList.toArray(new Measurement[myList.size()]);
    }

    /**
     * Update a measurement
     * @param id the id of the measurement to be updated
     */
    public void updateMeasurement(int id) {
        System.out.println("Update : " + id);
    }

    /**
     * Delete a measurement
     * @param id the id of the measurement to be deleted
     */
    public void deleteMeasurement(int id) {
        System.out.println("Delete : " + id);
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
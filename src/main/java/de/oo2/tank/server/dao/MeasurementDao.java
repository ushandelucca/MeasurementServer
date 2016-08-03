package de.oo2.tank.server.dao;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import de.oo2.tank.model.Measurement;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.jongo.Oid.withOid;

/**
 * Data Access Object for the measurements.
 */
public class MeasurementDao {
    private static final Logger logger = LoggerFactory.getLogger(MeasurementDao.class.getName());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private String dbName;
    private String host;
    private int port;

    /**
     * Constructor.
     *
     * @param dbName the name for the database     *
     * @param host
     * @param port
     */
    public MeasurementDao (String dbName, String host, int port) {
        this.dbName = dbName;
        this.host = host;
        this.port = port;
    }

    /**
     * Returns the database.
     *
     * @return the db
     */
    private DB getDatabase() {
        MongoClient mongoClient = null;

        try {
            mongoClient = new MongoClient(host, port);
        } catch (UnknownHostException e) {
            logger.error("Error while connecting to the database.", e);
        }

        return mongoClient.getDB(dbName);
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

        WriteResult result = measurements.save(measurement);

        // Measurement savedMeasurement = measurements.findOne("{ _id: " + result.getUpsertedId() + " })").as(Measurement.class);

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
     * @param query the query string
     * @param sort the sort string
     * @param limit the result limit
     * @return An array of the matching <code>Measurement</code> objects. If no <code>Measurement</code> matches
     * the period an empty array will be returned.
     * @throws MeasurementDataAccessException
     */
    public Measurement[] readMeasurementsWithQuery(String query, String sort, int limit) throws MeasurementDataAccessException {
        MongoCollection measurements = getMeasurements();
        MongoCursor<Measurement> all = null;

        // http://jongo.org/#querying

        try {
            all = measurements.find(query).sort(sort).limit(limit).as(Measurement.class);
        }
        catch (Exception e) {
            handleException("Error while selecting the measurements!", e);
        }

        List<Measurement> myList = Lists.newArrayList(all.iterator());

        if (myList == null) {
            return new Measurement[0];
        }

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
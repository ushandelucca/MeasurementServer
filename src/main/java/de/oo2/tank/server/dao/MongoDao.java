package de.oo2.tank.server.dao;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import de.oo2.tank.server.model.Measurement;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import static org.jongo.Oid.withOid;

/**
 * Data Access Object for the measurements in a mongo db.
 * See: https://www.mongodb.com/
 * Implemented with http://jongo.org
 */
public class MongoDao {
    // private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private static final Logger logger = LoggerFactory.getLogger(MongoDao.class.getName());
    private MongoClient mongoClient = null;

    private String dbName;
    private String host;
    private int port;

    /**
     * Constructor.
     *
     * @param dbName the name of the database
     * @param host the host of the database
     * @param port the port of the database
     */
    public MongoDao(String dbName, String host, int port) {
        this.dbName = dbName;
        this.host = host;
        this.port = port;

        logger.info("Creating DAO for host: '" + host + "', port: '" + port + "', db name: '" + dbName + "'.");
    }

    /**
     * Opens and returns the connection to the measurement db.
     *
     * @return the DB connection
     * @throws PersistenceException
     */
    protected MongoCollection getMeasurements() throws PersistenceException {
        MongoCollection measurements = null;

        try {
            if (mongoClient == null) {
                mongoClient = new MongoClient(host, port);
            }

            DB db = Objects.requireNonNull(mongoClient).getDB(dbName);
            Jongo jongo = new Jongo(db);

            measurements = jongo.getCollection("measurements");

        } catch (UnknownHostException e) {
            handleException("Error while connecting to the database.", e);
        }

        return measurements;
    }

    /**
     * Close the connection to the measurement db.
     * https://github.com/bguerout/jongo/issues/162
     * Well Jongo owns only a DB instance and so is not responsible of closing mongo connections (should be done through MongoClient instance)
     */
    protected void closeMeasurements() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    /**
     * Creates a new entry in the db and stores the given measurement date in the new row. The returned
     * <code>Measurement</code> object contains the unique id (primary key) of the measurement.
     *
     * @param measurement data to store
     * @return the stored measurement
     * @throws PersistenceException
     */
    public Measurement createMeasurement(Measurement measurement) throws PersistenceException {
        try {
            MongoCollection measurements = getMeasurements();
            measurements.save(measurement);
        } finally {
            closeMeasurements();
        }

        return measurement;
    }

    /**
     * Read a measurement by a given id.
     *
     * @param id of the measurement
     * @return the measurement if the measurement is not found in the db the return value is <code>null</code>.
     * @throws PersistenceException
     */
    public Measurement readMeasurementById(String id) throws PersistenceException {
        MongoCollection measurements = getMeasurements();
        Measurement m = null;

        try {
            try {
                m = measurements.findOne(withOid(id)).as(Measurement.class);
            } catch (Exception e) {
                handleException("Error while searching for id '" + id + "' the measurements!", e);
            }
        } finally {
            closeMeasurements();
        }

        return m;
    }

    /**
     * Read a measurement by a given begin and end of a time period.
     * See: http://jongo.org/#querying
     * @param queryParameters the parameters for the query
     * @return An array of the matching <code>Measurement</code> objects. If no <code>Measurement</code> matches
     * the period an empty array will be returned.
     * @throws PersistenceException
     */
    public Measurement[] readMeasurementsWithQuery(String queryParameters) throws PersistenceException {
        MeasurementQueryComposer queryComposer = new MeasurementQueryComposer(queryParameters);

        String query = queryComposer.getQuery();
        String sort = queryComposer.getSort();
        int limit = queryComposer.getLimit();

        MongoCollection measurements = getMeasurements();
        List<Measurement> myList = null;

        try {
            try {
                MongoCursor<Measurement> all = measurements.find(query).sort(sort).limit(limit).as(Measurement.class);
                myList = Lists.newArrayList(all.iterator());
                all.close();
            } catch (Exception e) {
                handleException("Error while selecting the measurements!", e);
            }
        } finally {
            closeMeasurements();
        }

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
     * @throws PersistenceException the exception that will be thrown
     */
    private void handleException(String message, Throwable throwable) throws PersistenceException {
        logger.error(message, throwable);
        throw new PersistenceException(message, throwable);
    }

}
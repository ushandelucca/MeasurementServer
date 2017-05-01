package de.oo2.tank.server.persistence;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import de.oo2.tank.server.model.Measurement;
import org.joda.time.DateTime;
import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.jongo.Oid.withOid;

/**
 * Data Access Object for the measurements in a mongo db.
 * See: https://www.mongodb.com/
 * Implemented with http://jongo.org
 */
public class MongoDao {
    // private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private static final int MAX_RESULT_COUNT = 1000;

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
     * @throws PersistenceException in case of failure
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
    void closeMeasurements() {
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
     * @throws PersistenceException in case of failure
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
     * @throws PersistenceException in case of failure
     */
    public Measurement readMeasurementWithId(String id) throws PersistenceException {
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
     * @throws PersistenceException in case of failure
     */
    public Measurement[] readMeasurementsWithQuery(Map<String, String[]> queryParameters) throws PersistenceException {
        QueryParser queryParser = new QueryParser();
        queryParser.setQuery(queryParameters);

        queryParser.checkQuery();

        MongoCollection measurements = getMeasurements();
        List<Measurement> myList = null;

        try {
            try {
                Find find;

                if (queryParser.hasDate() && queryParser.hasSensor()) {
                    DateTime dtBegin = setBeginOfDay(queryParser.getBeginDate());
                    DateTime dtEnd = setEndOfDay(queryParser.getEndDate());

                    find = measurements.find("{ timestamp: { $gte: #, $lte: # }, sensor: # }", dtBegin.toDate(), dtEnd.toDate(), queryParser.getSensor());
                } else if (queryParser.hasDate() && !queryParser.hasSensor()) {
                    DateTime dtBegin = setBeginOfDay(queryParser.getBeginDate());
                    DateTime dtEnd = setEndOfDay(queryParser.getEndDate());

                    find = measurements.find("{ timestamp: { $gte: #, $lte: # } }", dtBegin.toDate(), dtEnd.toDate());
                } else if (!queryParser.hasDate() && queryParser.hasSensor()) {
                    find = measurements.find("{ sensor: # }", queryParser.getSensor());
                } else {
                    find = measurements.find("{ }");
                }

                // TODO: reduce cyclomatic complexity: too much nested conditions
                if (queryParser.hasSort()) {

                    if (queryParser.isSortDateAsc()) {
                        find = find.sort("{ \"timestamp\": 1 }");
                    }
                    if (queryParser.isSortDateDesc()) {
                        find = find.sort("{ \"timestamp\": -1 }");
                    }
                }

                // limit the result set to a maximum
                if (queryParser.hasLimit()) {
                    find.limit(Math.min(queryParser.getLimit(), MAX_RESULT_COUNT));
                } else {
                    find.limit(MAX_RESULT_COUNT);
                }

                MongoCursor<Measurement> mongoCursor = find.as(Measurement.class);

                myList = Lists.newArrayList(mongoCursor.iterator());
                mongoCursor.close();
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
     * @param measurement the measurement to be updated
     * @throws PersistenceException in case of failure
     * @return the updated measurement
     */
    public Measurement updateMeasurement(Measurement measurement) throws PersistenceException {
        MongoCollection measurements = getMeasurements();

        try {
            try {
                WriteResult writeResult = measurements.update(withOid(measurement.getId())).with(measurement);

                if (writeResult.getN() != 1) {
                    throw new PersistenceException("");
                }

            } catch (Exception e) {
                handleException("Error while updating the measurement with id '" + measurement.getId() + "'", e);
            }
        } finally {
            closeMeasurements();
        }

        return measurement;
    }

    /**
     * Delete a measurement
     * @param id the id of the measurement to be deleted
     * @throws PersistenceException in case of failure
     */
    public void deleteMeasurement(String id) throws PersistenceException {
        MongoCollection measurements = getMeasurements();

        try {
            try {
                WriteResult writeResult = measurements.remove(withOid(id));

                if (writeResult.getN() != 1) {
                    throw new PersistenceException("");
                }

            } catch (Exception e) {
                handleException("Error while deleting the measurement with id '" + id + "'", e);
            }
        } finally {
            closeMeasurements();
        }
    }

    /**
     * Convenience method for the exception handling.
     *
     * @param message   the exception message
     * @param throwable the original exception
     * @throws PersistenceException in case of failure
     */
    private void handleException(String message, Throwable throwable) throws PersistenceException {
        logger.error(message, throwable);
        throw new PersistenceException(message, throwable);
    }

    /**
     * Modify the time of the given day to 00:00:00.
     *
     * @param begin the date to modify
     * @return the modified date
     */
    private DateTime setBeginOfDay(DateTime begin) {
        begin = begin.hourOfDay().setCopy(0);
        begin = begin.minuteOfHour().setCopy(0);
        begin = begin.secondOfMinute().setCopy(0);
        return begin;
    }

    /**
     * Modify the time of the given day to 23:59:59.
     *
     * @param end the date to modify
     * @return the modified date
     */
    private DateTime setEndOfDay(DateTime end) {
        end = end.hourOfDay().setCopy(23);
        end = end.minuteOfHour().setCopy(59);
        end = end.secondOfMinute().setCopy(59);
        return end;
    }

}
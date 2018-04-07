package de.oo2.m.server.measurement;

/**
 * This checked exception is thrown to report errors in the persistence layer.
 * For example: database is not accessible.
 */
public class MeasurementDaoException extends Exception {

    /**
     * Constructor for the <code>MeasurementDaoException</code>.
     *
     * @param message the message
     */
    public MeasurementDaoException(String message) {
        super(message);
    }

    /**
     * Constructor for the <code>MeasurementDaoException</code>.
     *
     * @param message the message
     * @param throwable the original exception
     */
    public MeasurementDaoException(String message, Throwable throwable) {
        super(message, throwable);
    }

}

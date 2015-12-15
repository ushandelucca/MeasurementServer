package de.oo2.tank.server.dao;

/**
 * The Data Access Exception is used to report errors during the db access.
 */
public class MeasurementDataAccessException extends Exception{

    /**
     * Constructor for the <code>MeasurementDataAccessException</code>.
     * @param message the message
     */
    public MeasurementDataAccessException(String message) {
        super(message);
    }

    /**
     * Constructor for the <code>MeasurementDataAccessException</code>.
     * @param throwable the original exception
     */
    public MeasurementDataAccessException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructor for the <code>MeasurementDataAccessException</code>.
     * @param message the message
     * @param throwable the original exception
     */
    public MeasurementDataAccessException(String message, Throwable throwable) {
        super(message, throwable);
    }

}

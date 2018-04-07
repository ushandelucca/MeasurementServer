package de.oo2.m.server.measurement;

/**
 * This checked exception is thrown to report errors in the model.
 */
public class MeasurementException extends Exception {

    /**
     * Constructor for the <code>MeasurementException</code>.
     *
     * @param message the message
     */
    public MeasurementException(String message) {
        super(message);
    }

}

package de.oo2.tank.server.measurement;

/**
 * This checked exception is thrown to report errors when a request is not authorised.
 */
public class NotAuthorisedException extends Exception {

    /**
     * Constructor for the <code>NotAuthorisedException</code>.
     *
     * @param message the message
     */
    public NotAuthorisedException(String message) {
        super(message);
    }

}

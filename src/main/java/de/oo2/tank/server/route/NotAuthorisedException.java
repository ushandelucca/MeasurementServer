package de.oo2.tank.server.route;

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

    /**
     * Constructor for the <code>NotAuthorisedException</code>.
     *
     * @param message   the message
     * @param throwable the original exception
     */
    public NotAuthorisedException(String message, Throwable throwable) {
        super(message, throwable);
    }

}

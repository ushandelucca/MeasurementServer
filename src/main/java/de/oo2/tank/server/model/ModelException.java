package de.oo2.tank.server.model;

/**
 * This checked exception is thrown to report errors in the model.
 */
public class ModelException extends Exception {

    /**
     * Constructor for the <code>ModelException</code>.
     *
     * @param message the message
     */
    public ModelException(String message) {
        super(message);
    }

    /**
     * Constructor for the <code>ModelException</code>.
     *
     * @param throwable the original exception
     */
    public ModelException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructor for the <code>ModelException</code>.
     *
     * @param message   the message
     * @param throwable the original exception
     */
    public ModelException(String message, Throwable throwable) {
        super(message, throwable);
    }

}

package de.oo2.tank.server.util;

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

}

package de.oo2.tank.server.dao;

/**
 * This checked exception is thrown to report errors in the persistence layer.
 * For example: database is not accessible.
 */
public class PersistenceException extends Exception {

    /**
     * Constructor for the <code>PersistenceException</code>.
     *
     * @param message the message
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Constructor for the <code>PersistenceException</code>.
     *
     * @param throwable the original exception
     */
    public PersistenceException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructor for the <code>PersistenceException</code>.
     *
     * @param message   the message
     * @param throwable the original exception
     */
    public PersistenceException(String message, Throwable throwable) {
        super(message, throwable);
    }

}

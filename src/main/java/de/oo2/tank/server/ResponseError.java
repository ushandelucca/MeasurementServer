package de.oo2.tank.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This class handles the error messages in the response.
 */
@ApiModel(description = "Error messages in the response")
public class ResponseError {

    // the error message
    @ApiModelProperty(required = true)
    private String message = "Error while processing the request!";

    /**
     * This constructor creates a response with a formatted message.
     * In case of a empty message a default message will be created.
     *
     * @param message the message
     * @param args    the arguments for the message
     * @see java.lang.String
     */
    public ResponseError(String message, String... args) {
        if (message != null) {
            this.message = String.format(message, args);
        }
    }

    /**
     * This constructor creates a response where the message is extracted from the given exception.
     * @param e the exception
     */
    public ResponseError(Exception e) {
        this(e.getMessage());
    }

    /**
     * Returns the message.
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }
}

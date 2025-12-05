package com.yoavmorahg.learner_app.Exception;

public class ResourceExistsException extends Exception {

    public ResourceExistsException() {
    }

    public ResourceExistsException(String message) {
        super(message);
    }

    public ResourceExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceExistsException(Throwable cause) {
        super(cause);
    }

    public ResourceExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

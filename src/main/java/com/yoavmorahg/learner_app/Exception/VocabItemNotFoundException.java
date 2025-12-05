package com.yoavmorahg.learner_app.Exception;

public class VocabItemNotFoundException extends ResourceNotFoundException {

    public VocabItemNotFoundException() {
    }

    public VocabItemNotFoundException(String message) {
        super(message);
    }

    public VocabItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VocabItemNotFoundException(Throwable cause) {
        super(cause);
    }

    public VocabItemNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.yoavmorahg.learner_app.Exception;

public class AudioDataNotFoundException extends ResourceNotFoundException {
    public AudioDataNotFoundException() {
        super();
    }

    public AudioDataNotFoundException(String message) {
        super(message);
    }

    public AudioDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AudioDataNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AudioDataNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

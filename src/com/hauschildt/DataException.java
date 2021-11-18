package com.hauschildt;

public class DataException extends Exception {
    public DataException() {
        super();
    }
    public DataException(String message) {
        super(message);
    }
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
    public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public DataException(Throwable cause) {
        super(cause);
    }
}

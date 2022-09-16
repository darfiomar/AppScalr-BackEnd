package io.creator.appscalr.exceptions;


public class AppscalrException extends RuntimeException {
    public AppscalrException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public AppscalrException(String exMessage) {
        super(exMessage);
    }
}

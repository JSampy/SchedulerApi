package ru.jsamkt.jocdoc.schedulerapi.exception;


public class SchedulerValidationException extends RuntimeException{

    public SchedulerValidationException(String message) {
        super(message);
    }

    public SchedulerValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

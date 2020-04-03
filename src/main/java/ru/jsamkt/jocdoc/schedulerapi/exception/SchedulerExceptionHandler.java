package ru.jsamkt.jocdoc.schedulerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SchedulerExceptionHandler {

    @ExceptionHandler(value = SchedulerValidationException.class)
    public ResponseEntity<Object> exception(SchedulerValidationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
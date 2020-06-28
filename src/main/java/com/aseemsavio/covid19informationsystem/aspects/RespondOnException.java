package com.aseemsavio.covid19informationsystem.aspects;

import com.aseemsavio.covid19informationsystem.exceptions.CovidInvalidDataException;
import com.aseemsavio.covid19informationsystem.exceptions.DataNotFoundException;
import com.aseemsavio.covid19informationsystem.model.Error;
import com.aseemsavio.covid19informationsystem.model.Response;
import com.aseemsavio.covid19informationsystem.service.LocalCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;

@ControllerAdvice("com.aseemsavio.covid19informationsystem.controller")
public class RespondOnException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Response> giveDataNotFoundResponse() {
        Response response = new Response();
        LocalCache localCache = LocalCache.getInstance();
        response.setStatus(STATUS_FAILED);
        response.setTotalResults(ZERO);
        Error error = new Error(ERROR_CODE_NOT_FOUND, ERROR_MSG_NOT_FOUND);
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setErrors(List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CovidInvalidDataException.class)
    public ResponseEntity<Response> giveInvalidDataExceptionResponse() {
        Response response = new Response();
        LocalCache localCache = LocalCache.getInstance();
        response.setStatus(STATUS_FAILED);
        response.setTotalResults(ZERO);
        Error error = new Error(ERROR_CODE_COVID_ERROR, ERROR_MSG_COVID_ERROR);
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setErrors(List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}

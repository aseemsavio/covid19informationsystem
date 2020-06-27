package com.aseemsavio.covid19informationsystem.aspects;

import com.aseemsavio.covid19informationsystem.model.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RespondOnExceptionTest {

    RespondOnException respondOnException;

    @BeforeEach
    public void init() {
        respondOnException = new RespondOnException();
    }

    @Test
    public void testGiveDataNotFoundResponse() {
        ResponseEntity<Response> responseEntity = respondOnException.giveDataNotFoundResponse();
        var response = responseEntity.getBody();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getErrors().get(ZERO).getErrorMessage(), ERROR_MSG_NOT_FOUND);
        assertEquals(response.getStatus(), STATUS_FAILED);
    }


}

package com.aseemsavio.covid19informationsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FilterErrorResponseTest {

    static FilterErrorResponse filterErrorResponse;

    @BeforeAll
    public static void init() {
        filterErrorResponse = new FilterErrorResponse();
        filterErrorResponse.setErrors(List.of(new Error(1, "error"), new Error(2, "occured")));
        filterErrorResponse.setTotalResults(10);
    }

    @Test
    public void testFilterErrorResponse() {
        Assertions.assertEquals(10,filterErrorResponse.getTotalResults());
        Assertions.assertEquals(1, filterErrorResponse.getErrors().get(0).getErrorCode());
        Assertions.assertEquals("error", filterErrorResponse.getErrors().get(0).getErrorMessage());
    }
}

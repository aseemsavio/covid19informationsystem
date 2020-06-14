package com.aseemsavio.covid19informationsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ErrorTest {

    static Error error;

    @BeforeAll
    public static void init() {
        error = new Error(1,"error");
    }

    @Test
    public void testError() {
        Assertions.assertEquals(1, error.getErrorCode());
        Assertions.assertEquals("error", error.getErrorMessage());
    }
}

package com.aseemsavio.covid19informationsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoronaCountTest {

    static CoronaCount coronaCount;

    @BeforeEach
    public void start() {
        coronaCount = new CoronaCount();
        coronaCount.setConfirmedCount(1000);
        coronaCount.setDeathCount(150);
    }

    @Test
    public void testCoronaCount() {
        Assertions.assertEquals(1000, coronaCount.getConfirmedCount());
        Assertions.assertEquals(150, coronaCount.getDeathCount());
    }

}

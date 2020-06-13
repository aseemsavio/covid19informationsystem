package com.aseemsavio.covid19informationsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CoronaCountTest {

    static CoronaCount coronaCount;

    @BeforeAll
    public static void init() {
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

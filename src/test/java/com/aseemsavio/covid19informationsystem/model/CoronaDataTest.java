package com.aseemsavio.covid19informationsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CoronaDataTest {

    static CoronaData coronaData;

    @BeforeAll
    public static void init() {

        coronaData = new CoronaData();
        coronaData.setId("corona");
        coronaData.setProvince("tamilnadu");
        coronaData.setCountry("india");
        coronaData.setLatitude(1.8);
        coronaData.setLongitude(2.3);
        coronaData.setConfirmedCount(Arrays.asList(123L, 345L, 456L));
        coronaData.setDeathCount(Arrays.asList(234L, 567L, 678L));

    }

    @Test
    public void testCoronaData() {
        Assertions.assertEquals("corona", coronaData.getId());
        Assertions.assertEquals("tamilnadu", coronaData.getProvince());
        Assertions.assertEquals("india", coronaData.getCountry());
        Assertions.assertEquals(1.8, coronaData.getLatitude());
        Assertions.assertEquals(2.3, coronaData.getLongitude());
        Assertions.assertEquals(123L, coronaData.getConfirmedCount().get(0));
        Assertions.assertEquals(234L, coronaData.getDeathCount().get(0));

    }

}

package com.aseemsavio.covid19informationsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoronaDataExtraTest {

    static CoronaDataExtra coronaDataExtra;

    @BeforeEach
    public void start() {
        coronaDataExtra = new CoronaDataExtra();
        coronaDataExtra.setTotalConfirmed(123L);
        coronaDataExtra.setTotalDeaths(234L);
        coronaDataExtra.setLastUpdated("today");
    }

    @Test
    public void testCoronaDataExtra() {
        Assertions.assertEquals(123L, coronaDataExtra.getTotalConfirmed());
        Assertions.assertEquals(234L, coronaDataExtra.getTotalDeaths());
        Assertions.assertEquals("today", coronaDataExtra.getLastUpdated());
    }
}

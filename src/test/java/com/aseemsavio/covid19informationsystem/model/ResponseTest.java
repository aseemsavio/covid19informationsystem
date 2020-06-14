package com.aseemsavio.covid19informationsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ResponseTest {
     static Response response;

     @BeforeAll
    public static void init() {
         response = new Response();
         CoronaDataExtra coronaDataExtra = new CoronaDataExtra();
         coronaDataExtra.setLastUpdated("today");
         coronaDataExtra.setTotalDeaths(10L);
         coronaDataExtra.setTotalConfirmed(20L);
         coronaDataExtra.setConfirmedCount(Arrays.asList(15L, 34L, 56L));
         coronaDataExtra.setDeathCount(Arrays.asList(40L, 89L, 90L));

         CoronaDataExtra coronaDataExtra1 = new CoronaDataExtra();
         coronaDataExtra1.setDeathCount(Arrays.asList(12L, 76L, 89L));
         coronaDataExtra1.setConfirmedCount(Arrays.asList(9L, 54L, 87L));
         coronaDataExtra1.setTotalConfirmed(74L);
         coronaDataExtra1.setTotalDeaths(87L);
         coronaDataExtra1.setLastUpdated("yesterday");

         CoronaData coronaData = new CoronaData();
         coronaData.setDeathCount(Arrays.asList(41L, 56L, 98L));
         coronaData.setConfirmedCount(Arrays.asList(78L, 93L, 45L));
         coronaData.setLongitude(6.7);
         coronaData.setLatitude(4.2);
         coronaData.setCountry("india");
         coronaData.setProvince("tamilnadu");
         coronaData.setId("one");

         CoronaData coronaData1 = new CoronaData();
         coronaData1.setDeathCount(Arrays.asList(80L, 95L, 78L));
         coronaData1.setConfirmedCount(Arrays.asList(34L, 45L, 56L));
         coronaData1.setLongitude(2.7);
         coronaData1.setLatitude(5.2);
         coronaData1.setCountry("america");
         coronaData1.setProvince("kanyakumari");
         coronaData1.setId("two");

         response.setLastUpdatedMinutes(23L);
         response.setStatus("Success");
         response.setTotalResults(100);
         response.setCountData(Arrays.asList(coronaDataExtra, coronaDataExtra1));
         response.setDates(Arrays.asList("24/4", "25/4"));
         response.setErrors(Arrays.asList(new Error(1, "error"), new Error(2,"occured")));
         response.setTimeSeriesData(Arrays.asList(coronaData, coronaData1));
         response.setCountries(Arrays.asList("india", "america", "italy"));
         response.setProvinces(Arrays.asList("tamilnadu", "kerela", "kanyakumari"));
     }

     @Test
    public void testResponse() {
         Assertions.assertEquals(23L, response.getLastUpdatedMinutes());
         Assertions.assertEquals("Success", response.getStatus());
         Assertions.assertEquals(100, response.getTotalResults());
         Assertions.assertEquals(10L, response.getCountData().get(0).getTotalDeaths());
         Assertions.assertEquals("24/4", response.getDates().get(0));
         Assertions.assertEquals("occured", response.getErrors().get(1).getErrorMessage());
         Assertions.assertEquals("kanyakumari", response.getTimeSeriesData().get(1).getProvince());
         Assertions.assertEquals("italy", response.getCountries().get(2));
         Assertions.assertEquals("kerela", response.getProvinces().get(1));
     }
}

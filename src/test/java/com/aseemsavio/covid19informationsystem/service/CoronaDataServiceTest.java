package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.exceptions.CovidInvalidDataException;
import com.aseemsavio.covid19informationsystem.exceptions.DataNotFoundException;
import com.aseemsavio.covid19informationsystem.model.CoronaData;
import com.aseemsavio.covid19informationsystem.model.CoronaDataExtra;
import com.aseemsavio.covid19informationsystem.model.User;
import com.aseemsavio.covid19informationsystem.repository.CoronaDataRepository;
import com.aseemsavio.covid19informationsystem.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoronaDataServiceTest {

    @InjectMocks
    CoronaDataService coronaDataService;

    @Mock
    UserRepository userRepository;

    @Mock
    CoronaDataRepository coronaDataRepository;

    List<User> userList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deleteFromCollectionTest() {
        coronaDataService.deleteFromCollection(List.of("a", "b", "c"));
    }

    @Test
    public void updateUsersInCacheTest() {
        giveUsers();
        LocalCache localCache = LocalCache.getInstance();
        assertNull(localCache.getUsers());
        coronaDataService.updateUsersInCache(userList, localCache);
        assertEquals(2, localCache.getUsers().size());
    }

    @Test
    public void getAllUsersTest() {
        giveUsers();
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<User> users = coronaDataService.getAllUsers();
        assertEquals(users.size(), 2);
        assertEquals(users.get(0).isActive(), true);
    }

    @Test
    public void findByCountryCountTest() throws CovidInvalidDataException, DataNotFoundException {
        List<CoronaData> data = getCoronaDataList();
        Mockito.when(coronaDataRepository.findByCountry(Mockito.anyString())).thenReturn(data);
        List<CoronaDataExtra> result = coronaDataService.findByCountryCount("myCountry");
        assertEquals("b", result.get(0).getCountry());
        assertNull(result.get(0).getId());
        assertNull(result.get(0).getProvince());
        assertEquals(1D, result.get(0).getLatitude());
        assertEquals(2D, result.get(0).getLongitude());
        assertEquals(6L, result.get(0).getTotalDeaths());
        assertEquals(15L, result.get(0).getTotalConfirmed());
    }

    @Test
    public void findByCountryCountTestIfDataNull() throws CovidInvalidDataException, DataNotFoundException {
        Mockito.when(coronaDataRepository.findByCountry(Mockito.anyString())).thenReturn(null);
        assertThrows(DataNotFoundException.class, () -> {
            List<CoronaDataExtra> result = coronaDataService.findByCountryCount("myCountry");
        });
    }

    @Test
    public void findByCountryCountTestInvalidDataException() throws CovidInvalidDataException, DataNotFoundException {
        Mockito.when(coronaDataRepository.findByCountry(Mockito.anyString())).thenThrow(NullPointerException.class);
        assertThrows(CovidInvalidDataException.class, () -> {
            List<CoronaDataExtra> result = coronaDataService.findByCountryCount("hello");
        });
    }

    @Test
    public void findByProvinceCountTest() throws CovidInvalidDataException, DataNotFoundException {
        List<CoronaData> data = getCoronaDataList();
        Mockito.when(coronaDataRepository.findByProvince(Mockito.anyString())).thenReturn(data);
        List<CoronaDataExtra> result = coronaDataService.findByProvinceCount("prov");
        assertEquals("b", result.get(0).getCountry());
        assertNull(result.get(0).getId());
        assertEquals(1D, result.get(0).getLatitude());
        assertEquals(2D, result.get(0).getLongitude());
        assertEquals(2L, result.get(0).getTotalDeaths());
        assertEquals(5L, result.get(0).getTotalConfirmed());
    }

    @Test
    public void findByProvinceCountTestException() throws CovidInvalidDataException, DataNotFoundException {
        List<CoronaData> data = getCoronaDataList();
        Mockito.when(coronaDataRepository.findByProvince(Mockito.anyString())).thenThrow(NullPointerException.class);
        assertThrows(CovidInvalidDataException.class, () -> {
            List<CoronaDataExtra> result = coronaDataService.findByProvinceCount("prov");
        });
    }

    @Test
    public void findByProvinceCountTestNullData() throws CovidInvalidDataException, DataNotFoundException {
        Mockito.when(coronaDataRepository.findByProvince(Mockito.anyString())).thenReturn(null);
        assertThrows(DataNotFoundException.class, () -> {
            List<CoronaDataExtra> result = coronaDataService.findByProvinceCount("prov");
        });
    }

    @Test
    public void findByCountryTest() throws CovidInvalidDataException, DataNotFoundException {
        List<CoronaData> data = getCoronaDataList();
        Mockito.when(coronaDataRepository.findByCountry(Mockito.anyString())).thenReturn(data);
        List<CoronaData> coronaData = coronaDataService.findByCountry("myCOuntry");
        assertEquals("b", coronaData.get(0).getCountry());
        assertEquals(1D, coronaData.get(0).getLatitude());
    }

    @Test
    public void findByCountryTestInvalidDataException() throws CovidInvalidDataException, DataNotFoundException {
        List<CoronaData> data = getCoronaDataList();
        Mockito.when(coronaDataRepository.findByCountry(Mockito.anyString())).thenThrow(NullPointerException.class);
        assertThrows(CovidInvalidDataException.class, () -> {
            List<CoronaData> coronaData = coronaDataService.findByCountry("myCOuntry");
        });
    }

    @Test
    public void findByCountryTestDataNotFoundException() throws CovidInvalidDataException, DataNotFoundException {
        Mockito.when(coronaDataRepository.findByCountry(Mockito.anyString())).thenReturn(null);
        assertThrows(DataNotFoundException.class, () -> {
            List<CoronaData> coronaData = coronaDataService.findByCountry("myCOuntry");
        });
    }

    @Test
    public void findByProvincesTest() throws CovidInvalidDataException, DataNotFoundException {
        List<CoronaData> data = getCoronaDataList();
        Mockito.when(coronaDataRepository.findByProvince(Mockito.anyString())).thenReturn(data);
        List<CoronaData> coronaData = coronaDataService.findByProvinces("prov");
        assertEquals("b", coronaData.get(0).getCountry());
        assertEquals(1D, coronaData.get(0).getLatitude());
    }

    @Test
    public void findByProvincesTestException() throws CovidInvalidDataException, DataNotFoundException {
        Mockito.when(coronaDataRepository.findByProvince(Mockito.anyString())).thenThrow(NullPointerException.class);
        assertThrows(CovidInvalidDataException.class, () -> {
            List<CoronaData> coronaData = coronaDataService.findByProvinces("prov");
        });
    }

    @Test
    public void findByProvincesTestNoDataException() throws CovidInvalidDataException, DataNotFoundException {
        Mockito.when(coronaDataRepository.findByProvince(Mockito.anyString())).thenReturn(null);
        assertThrows(DataNotFoundException.class, () -> {
            List<CoronaData> coronaData = coronaDataService.findByProvinces("prov");
        });
    }

    @Test
    public void findAllCountriesTest() throws DataNotFoundException {
        List<CoronaData> data = getCoronaDataList();
        Mockito.when(coronaDataRepository.findAll()).thenReturn(data);
        List<String> strings = coronaDataService.findAllCountries();
        assertTrue(strings.size() == 1);
        assertTrue(strings.get(0).equals("b"));
    }

    @Test
    public void findAllCountriesTestException() throws DataNotFoundException {
        Mockito.when(coronaDataRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(DataNotFoundException.class, () -> {
            List<String> strings = coronaDataService.findAllCountries();
        });
    }

    @Test
    public void findAllProvincesTest() throws DataNotFoundException {
        List<CoronaData> data = getCoronaDataList();
        Mockito.when(coronaDataRepository.findAll()).thenReturn(data);
        List<String> strings = coronaDataService.findAllProvinces();
        assertTrue(strings.size() == 1);
        assertTrue(strings.get(0).equals("a"));
    }

    @Test
    public void findAllProvincesTestException() throws DataNotFoundException {
        Mockito.when(coronaDataRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(DataNotFoundException.class, () -> {
            List<String> strings = coronaDataService.findAllProvinces();
        });
    }

    private List<CoronaData> getCoronaDataList() {
        CoronaData coronaData = new CoronaData();
        coronaData.setId("1");
        coronaData.setLatitude(1D);
        coronaData.setLongitude(2D);
        coronaData.setProvince("a");
        coronaData.setCountry("b");
        coronaData.setConfirmedCount(List.of(1L, 2L, 3L, 4L, 5L));
        coronaData.setDeathCount(List.of(0L, 0L, 1L, 1L, 2L));

        CoronaData coronaData1 = new CoronaData();
        coronaData1.setId("2");
        coronaData1.setLatitude(1D);
        coronaData1.setLongitude(2D);
        coronaData1.setProvince("a");
        coronaData1.setCountry("b");
        coronaData1.setConfirmedCount(List.of(1L, 2L, 3L, 4L, 5L));
        coronaData1.setDeathCount(List.of(0L, 0L, 1L, 1L, 2L));

        CoronaData coronaData2 = new CoronaData();
        coronaData2.setId("2");
        coronaData2.setLatitude(1D);
        coronaData2.setLongitude(2D);
        coronaData2.setProvince("a");
        coronaData2.setCountry("b");
        coronaData2.setConfirmedCount(List.of(1L, 2L, 3L, 4L, 5L));
        coronaData2.setDeathCount(List.of(0L, 0L, 1L, 1L, 2L));
        return List.of(coronaData, coronaData1, coronaData2);
    }

    private void giveUsers() {
        userList = new ArrayList<>(2);
        User user = new User();
        user.setFirstName("a");
        user.setLastName("a");
        user.setEmail("a");
        user.setCompany("a");
        user.setAuthorizationKey("a");
        user.setActive(true);
        user.setPlan(0);

        User user2 = new User();
        user2.setFirstName("b");
        user2.setLastName("b");
        user2.setEmail("b");
        user2.setCompany("b");
        user2.setAuthorizationKey("b");
        user2.setActive(true);
        user2.setPlan(0);

        userList.add(user);
        userList.add(user2);
    }

    @AfterEach
    public void tearDown() {
        userList = null;
    }
}

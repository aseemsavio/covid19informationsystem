package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.exceptions.CovidInvalidDataException;
import com.aseemsavio.covid19informationsystem.exceptions.DataNotFoundException;
import com.aseemsavio.covid19informationsystem.model.CoronaCount;
import com.aseemsavio.covid19informationsystem.model.CoronaData;
import com.aseemsavio.covid19informationsystem.model.CoronaDataExtra;
import com.aseemsavio.covid19informationsystem.model.User;
import com.aseemsavio.covid19informationsystem.repository.CoronaDataRepository;
import com.aseemsavio.covid19informationsystem.repository.UserRepository;
import com.aseemsavio.covid19informationsystem.utils.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;
import static com.aseemsavio.covid19informationsystem.utils.Type.CONFIRMED;
import static com.aseemsavio.covid19informationsystem.utils.Type.DEATH;

@Service
public class CoronaDataService {

    @Value("${timeseries.source.url}")
    private String SOURCE_URL;

    @Autowired
    private CoronaDataRepository dataRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(CoronaDataService.class);

    /**
     * Reads the CSV file
     *
     * @return
     * @throws MalformedURLException
     */
    public List<CoronaData> readCSV(String fileName, Type type) throws MalformedURLException {
        log.info("Parsing " + type.toString() + " data starts");
        var fullURL = SOURCE_URL + fileName;
        var url = new URL(fullURL);
        var line = EMPTY_STRING;

        List<CoronaData> coronaDataList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while ((line = bufferedReader.readLine()) != NULL) {
                if (line.startsWith(PROVINCE)) {
                    List<String> strings = Arrays.asList(line.split(COMMA));
                    LocalCache localCache = LocalCache.getInstance();
                    localCache.setDates(strings.stream()
                            .filter(record -> strings.indexOf(record) > THREE)
                            .collect(Collectors.toList()));
                } else {
                    List<String> strings = Arrays.asList(line.split(COMMA));
                    CoronaData data = new CoronaData();
                    data.setProvince(strings.get(ZERO));
                    data.setCountry(strings.get(ONE));
                    try {
                        data.setLatitude(Double.parseDouble(strings.get(TWO)));
                        data.setLongitude(Double.parseDouble(strings.get(THREE)));
                        if (type.equals(CONFIRMED)) {
                            data.setConfirmedCount(strings.stream()
                                    .filter(record -> strings.indexOf(record) > THREE)
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList()));
                        } else if (type.equals(DEATH)) {
                            data.setDeathCount(strings.stream()
                                    .filter(record -> strings.indexOf(record) > THREE)
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList()));
                        } /*else if (type.equals(RECOVERED)) {
                            data.setRecoveredCount(strings.stream()
                                    .filter(record -> strings.indexOf(record) > THREE)
                                    .map(record -> Long.parseLong(record))
                                    .collect(Collectors.toList()));
                        }*/
                        coronaDataList.add(data);
                    } catch (Exception e) {
                        log.error("Exception occurred while parsing data for + " + strings.get(ZERO) + " " + strings.get(ONE) + ": " + e);
                    }
                }
            }
        } catch (Exception exception) {
            log.error("Unexpected Error occurred while parsing data: " + exception);
        }
        log.info("Parsing CSV Data ended.");
        return coronaDataList;
    }

    /**
     * Edits the existing CoronaData List
     *
     * @param existingDataList
     * @param fileName
     * @param type
     * @return
     * @throws MalformedURLException
     */
    public List<CoronaData> editExistingList(List<CoronaData> existingDataList, String fileName, Type type) throws MalformedURLException {
        var newList = readCSV(fileName, type);
        if (existingDataList.size() == newList.size()) {
            IntStream.iterate(ZERO, i -> (i < existingDataList.size()), i -> i + ONE).forEach(i -> {
                if ((existingDataList.get(i).getCountry().equals(newList.get(i).getCountry())) &&
                        (existingDataList.get(i).getProvince().equals(newList.get(i).getProvince()))) {
                    if (type.equals(DEATH))
                        existingDataList.get(i).setDeathCount(newList.get(i).getDeathCount());
                    else if (type.equals(CONFIRMED))
                        existingDataList.get(i).setConfirmedCount(newList.get(i).getConfirmedCount());
                    /*else if (type.equals(RECOVERED))
                        existingDataList.get(i).setRecoveredCount(newList.get(i).getRecoveredCount());*/
                }
            });
            /*for (int i = 0; i < existingDataList.size(); i++) {
                if ((existingDataList.get(i).getCountry().equals(newList.get(i).getCountry())) &&
                        (existingDataList.get(i).getProvince().equals(newList.get(i).getProvince()))) {
                    if (type.equals(DEATH))
                        existingDataList.get(i).setDeathCount(newList.get(i).getDeathCount());
                    else if (type.equals(CONFIRMED))
                        existingDataList.get(i).setConfirmedCount(newList.get(i).getConfirmedCount());
                    *//*else if (type.equals(RECOVERED))
                        existingDataList.get(i).setRecoveredCount(newList.get(i).getRecoveredCount());*//*
                }
            }*/
        }
        return existingDataList;
    }

    /**
     * Saves all the individual records.
     *
     * @param coronaDataList
     * @return
     */
    public List<String> saveToCollection(List<CoronaData> coronaDataList) {
        log.info("Saving Data to Collection...");
        return dataRepository.saveAll(coronaDataList)
                .stream()
                .map(CoronaData::getId)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all the data in the DB irrespective of any condition.
     *
     * @return
     */
    public List<CoronaData> findAllData() throws DataNotFoundException {
        List<CoronaData> data;
        try {
            data = dataRepository.findAll().
                    stream().
                    peek(record -> {
                        record.setId(NULL);
                        if (record.getProvince() == NULL || record.getProvince().equals(EMPTY_STRING))
                            record.setProvince(NULL);
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataNotFoundException();
        }
        return data;
    }

    /**
     * Gives today's count instead of the time series.
     *
     * @return
     */
    public List<CoronaDataExtra> findAllCount() throws DataNotFoundException {
        List<CoronaDataExtra> extra;
        try {
            var data = dataRepository.findAll();
            extra = data.stream().map(record -> {
                CoronaDataExtra dataExtra = new CoronaDataExtra();
                dataExtra.setId(NULL);
                dataExtra.setCountry(record.getCountry());
                if (record.getProvince() == NULL || record.getProvince().equals(EMPTY_STRING))
                    dataExtra.setProvince(NULL);
                else dataExtra.setProvince(record.getProvince());
                dataExtra.setLatitude(record.getLatitude());
                dataExtra.setLongitude(record.getLongitude());
                dataExtra.setTotalConfirmed(record.getConfirmedCount().get(record.getConfirmedCount().size() - ONE));
                dataExtra.setTotalDeaths(record.getDeathCount().get(record.getDeathCount().size() - ONE));
                //dataExtra.setTodaysRecovered(record.getRecoveredCount().get(record.getRecoveredCount().size() - 1));
                return dataExtra;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataNotFoundException();
        }
        return extra;
    }

    /**
     * Return all the provinces for which data is available.
     *
     * @return
     */
    public List<String> findAllProvinces() throws DataNotFoundException {
        List<String> data;
        try {
            data = dataRepository.findAll()
                    .stream()
                    .map(record -> record.getProvince() == NULL || record.getProvince().equals(EMPTY_STRING) ? EMPTY_STRING : record.getProvince())
                    .filter(record -> !record.equals(EMPTY_STRING))
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataNotFoundException();
        }
        return data;
    }

    /**
     * Returns all the countries for which data is available.
     *
     * @return
     */
    public List<String> findAllCountries() throws DataNotFoundException {
        List<String> data;
        try {
            data = dataRepository.findAll()
                    .stream()
                    .map(record -> record.getCountry() == NULL || record.getCountry().equals(EMPTY_STRING) ? EMPTY_STRING : record.getCountry())
                    .filter(record -> !record.equals(EMPTY_STRING))
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataNotFoundException();
        }
        return data;
    }

    /**
     * Finds CoronaData by provinces
     *
     * @param province
     * @return
     */
    public List<CoronaData> findByProvinces(String province) throws DataNotFoundException, CovidInvalidDataException {
        List<CoronaData> data;
        try {
            data = dataRepository.findByProvince(province);
        } catch (Exception e) {
            throw new CovidInvalidDataException();
        }
        if (data == null || data.size() == ZERO)
            throw new DataNotFoundException();
        return data;
    }

    /**
     * Finds Corona data by country
     *
     * @param country
     * @return
     */
    public List<CoronaData> findByCountry(String country) throws DataNotFoundException, CovidInvalidDataException {
        List<CoronaData> data;
        try {
            data = dataRepository.findByCountry(country);
        } catch (Exception e) {
            throw new CovidInvalidDataException();
        }
        if (data == null || data.size() == ZERO)
            throw new DataNotFoundException();
        return data;
    }

    public List<CoronaDataExtra> findByProvinceCount(String province) throws DataNotFoundException, CovidInvalidDataException {
        List<CoronaData> data;
        try {
            data = dataRepository.findByProvince(province);
        } catch (Exception e) {
            throw new CovidInvalidDataException();
        }
        if (data == null || data.size() == ZERO)
            throw new DataNotFoundException();
        var extra = data.stream().map(record -> {
            CoronaDataExtra dataExtra = new CoronaDataExtra();
            dataExtra.setId(NULL);
            dataExtra.setCountry(record.getCountry());
            if (record.getProvince() == NULL || record.getProvince().equals(EMPTY_STRING))
                dataExtra.setProvince(NULL);
            else dataExtra.setProvince(record.getProvince());
            dataExtra.setLatitude(record.getLatitude());
            dataExtra.setLongitude(record.getLongitude());
            dataExtra.setTotalConfirmed(record.getConfirmedCount().get(record.getConfirmedCount().size() - ONE));
            dataExtra.setTotalDeaths(record.getDeathCount().get(record.getDeathCount().size() - ONE));
            //dataExtra.setTodaysRecovered(record.getRecoveredCount().get(record.getRecoveredCount().size() - 1));
            return dataExtra;
        }).collect(Collectors.toList());
        return extra;
    }

    public List<CoronaDataExtra> findByCountryCount(String country) throws CovidInvalidDataException, DataNotFoundException {
        List<CoronaData> data;
        try {
            data = dataRepository.findByCountry(country);
        } catch (Exception e) {
            throw new CovidInvalidDataException();
        }
        if (data == null || data.size() == ZERO)
            throw new DataNotFoundException();
        var dataExtra = new CoronaDataExtra();
        try {
            dataExtra.setId(NULL);
            dataExtra.setCountry(data.get(ZERO).getCountry());
            dataExtra.setProvince(NULL);
            dataExtra.setLatitude(data.get(ZERO).getLatitude());
            dataExtra.setLongitude(data.get(ZERO).getLongitude());
            CoronaCount coronaCount = new CoronaCount();
            data.forEach(datum -> {
                coronaCount.setConfirmedCount(coronaCount.getConfirmedCount() + datum.getConfirmedCount().get(datum.getConfirmedCount().size() - ONE));
                coronaCount.setDeathCount(coronaCount.getDeathCount() + datum.getDeathCount().get(datum.getDeathCount().size() - ONE));
            });
            dataExtra.setTotalConfirmed(coronaCount.getConfirmedCount());
            dataExtra.setTotalDeaths(coronaCount.getDeathCount());
        } catch (Exception e) {
            throw new CovidInvalidDataException();
        }
        return List.of(dataExtra);
    }

    public List<User> getAllUsers() {
        log.info("Getting All Users from the Database...");
        return userRepository.findAll();
    }

    public void updateUsersInCache(List<User> users, LocalCache localCache) {
        var userMap = users.stream().collect(Collectors.toMap(User::getAuthorizationKey, user -> user));
        localCache.setUsers(userMap);
        log.info("Users Map updated successfully.");
    }

    public void deleteFromCollection(List<String> ids) {
        for (String id : ids) {
            dataRepository.deleteById(id);
        }
        log.info("Deleted old data from collection successfully.");
    }
}

package com.aseemsavio.covid19informationsystem.service;

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
import java.util.Map;
import java.util.stream.Collectors;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.COMMA;
import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.EMPTY_STRING;
import static com.aseemsavio.covid19informationsystem.utils.Type.*;

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
        String fullURL = SOURCE_URL + fileName;
        URL url = new URL(fullURL);
        String line = EMPTY_STRING;

        List<CoronaData> coronaDataList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while ((line = bufferedReader.readLine()) != null) {
                if(line.startsWith("Province")) {
                    List<String> strings = Arrays.asList(line.split(COMMA));
                    LocalCache localCache = LocalCache.getInstance();
                    localCache.setDates(strings.stream()
                            .filter(record -> strings.indexOf(record) > 3)
                            .collect(Collectors.toList()));
                } else {
                    List<String> strings = Arrays.asList(line.split(COMMA));
                    int length = strings.size();
                    CoronaData data = new CoronaData();
                    data.setProvince(strings.get(0));
                    data.setCountry(strings.get(1));
                    try {
                        data.setLatitude(Double.parseDouble(strings.get(2)));
                        data.setLongitude(Double.parseDouble(strings.get(3)));
                        if (type.equals(CONFIRMED)) {
                            data.setConfirmedCount(strings.stream()
                                    .filter(record -> strings.indexOf(record) > 3)
                                    .map(record -> Long.parseLong(record))
                                    .collect(Collectors.toList()));
                        } else if (type.equals(DEATH)) {
                            data.setDeathCount(strings.stream()
                                    .filter(record -> strings.indexOf(record) > 3)
                                    .map(record -> Long.parseLong(record))
                                    .collect(Collectors.toList()));
                        } /*else if (type.equals(RECOVERED)) {
                            data.setRecoveredCount(strings.stream()
                                    .filter(record -> strings.indexOf(record) > 3)
                                    .map(record -> Long.parseLong(record))
                                    .collect(Collectors.toList()));
                        }*/
                        coronaDataList.add(data);
                    } catch (Exception e) {
                        log.error("Exception occurred while parsing data for + " + strings.get(0) + " " + strings.get(1) + ": " + e);
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
        List<CoronaData> newList = readCSV(fileName, type);
        if (existingDataList.size() == newList.size()) {
            for (int i = 0; i < existingDataList.size(); i++) {
                if ((existingDataList.get(i).getCountry().equals(newList.get(i).getCountry())) &&
                        (existingDataList.get(i).getProvince().equals(newList.get(i).getProvince()))) {
                    if (type.equals(DEATH))
                        existingDataList.get(i).setDeathCount(newList.get(i).getDeathCount());
                    else if (type.equals(CONFIRMED))
                        existingDataList.get(i).setConfirmedCount(newList.get(i).getConfirmedCount());
                    /*else if (type.equals(RECOVERED))
                        existingDataList.get(i).setRecoveredCount(newList.get(i).getRecoveredCount());*/
                }
            }
        }
        log.info("Data: " + existingDataList);
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
                .stream().map(record -> record.getId())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all the data in the DB irrespective of any condition.
     *
     * @return
     */
    public List<CoronaData> findAllData() {
        return dataRepository.findAll().stream().map(record -> {
            record.setId(null);
            if (record.getProvince() == null || record.getProvince().equals(EMPTY_STRING))
                record.setProvince(null);
            return record;
        }).collect(Collectors.toList());
    }

    /**
     * Gives today's count instead of the time series.
     *
     * @return
     */
    public List<CoronaDataExtra> findAllCount() {
        List<CoronaData> data = dataRepository.findAll();
        int size = data.size();
        List<CoronaDataExtra> extra = data.stream().map(record -> {
            CoronaDataExtra dataExtra = new CoronaDataExtra();
            dataExtra.setId(null);
            dataExtra.setCountry(record.getCountry());
            if (record.getProvince() == null || record.getProvince().equals(EMPTY_STRING))
                dataExtra.setProvince(null);
            else dataExtra.setProvince(record.getProvince());
            dataExtra.setLatitude(record.getLatitude());
            dataExtra.setLongitude(record.getLongitude());
            dataExtra.setTodaysConfirmed(record.getConfirmedCount().get(record.getConfirmedCount().size() - 1));
            dataExtra.setTodaysDeaths(record.getDeathCount().get(record.getDeathCount().size() - 1));
            //dataExtra.setTodaysRecovered(record.getRecoveredCount().get(record.getRecoveredCount().size() - 1));
            return dataExtra;
        }).collect(Collectors.toList());
        return extra;
    }

    /**
     * Return all the provinces for which data is available.
     *
     * @return
     */
    public List<String> findAllProvinces() {
        return dataRepository.findAll()
                .stream()
                .map(record -> record.getProvince() == null || record.getProvince().equals(EMPTY_STRING) ? EMPTY_STRING : record.getProvince())
                .filter(record -> !record.equals(EMPTY_STRING))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Returns all the countries for which data is available.
     *
     * @return
     */
    public List<String> findAllCountries() {
        return dataRepository.findAll()
                .stream()
                .map(record -> record.getCountry() == null || record.getCountry().equals(EMPTY_STRING) ? EMPTY_STRING : record.getCountry())
                .filter(record -> !record.equals(EMPTY_STRING))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Finds CoronaData by provinces
     *
     * @param province
     * @return
     */
    public List<CoronaData> findByProvinces(String province) {
        return dataRepository.findByProvince(province);
    }

    /**
     * Finds Corona data by country
     *
     * @param country
     * @return
     */
    public List<CoronaData> findByCountry(String country) {
        return dataRepository.findByCountry(country);
    }

    public List<CoronaDataExtra> findByProvinceCount(String province) {
        List<CoronaData> data = dataRepository.findByProvince(province);
        List<CoronaDataExtra> extra = data.stream().map(record -> {
            CoronaDataExtra dataExtra = new CoronaDataExtra();
            dataExtra.setId(null);
            dataExtra.setCountry(record.getCountry());
            if (record.getProvince() == null || record.getProvince().equals(EMPTY_STRING))
                dataExtra.setProvince(null);
            else dataExtra.setProvince(record.getProvince());
            dataExtra.setLatitude(record.getLatitude());
            dataExtra.setLongitude(record.getLongitude());
            dataExtra.setTodaysConfirmed(record.getConfirmedCount().get(record.getConfirmedCount().size() - 1));
            dataExtra.setTodaysDeaths(record.getDeathCount().get(record.getDeathCount().size() - 1));
            //dataExtra.setTodaysRecovered(record.getRecoveredCount().get(record.getRecoveredCount().size() - 1));
            return dataExtra;
        }).collect(Collectors.toList());
        return extra;
    }

    public List<CoronaDataExtra> findByCountryCount(String country) {
        List<CoronaData> data = dataRepository.findByCountry(country);

        CoronaDataExtra dataExtra = new CoronaDataExtra();
        dataExtra.setId(null);
        dataExtra.setCountry(data.get(0).getCountry());
        dataExtra.setProvince(null);
        dataExtra.setLatitude(data.get(0).getLatitude());
        dataExtra.setLongitude(data.get(0).getLongitude());
        CoronaCount coronaCount = new CoronaCount();
        data.stream().forEach(datum -> {
            coronaCount.setConfirmedCount(coronaCount.getConfirmedCount() + datum.getConfirmedCount().get(datum.getConfirmedCount().size() - 1));
            coronaCount.setDeathCount(coronaCount.getDeathCount() + datum.getDeathCount().get(datum.getDeathCount().size() - 1));
        });
        dataExtra.setTodaysConfirmed(coronaCount.getConfirmedCount());
        dataExtra.setTodaysDeaths(coronaCount.getDeathCount());
        return Arrays.asList(dataExtra);
    }

    public List<User> getAllUsers() {
        log.info("Getting All Users from the Database...");
        return userRepository.findAll();
    }

    public void updateUsersInCache(List<User> users) {
        LocalCache localCache = LocalCache.getInstance();
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getAuthorizationKey, user -> user));
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

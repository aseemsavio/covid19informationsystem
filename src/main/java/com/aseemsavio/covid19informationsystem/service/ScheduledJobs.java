package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.model.CoronaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;
import static com.aseemsavio.covid19informationsystem.utils.Type.*;

@Service
public class ScheduledJobs {

    @Autowired
    CoronaDataService dataService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledJobs.class);

    public void updateUserDetailsInCache() {
        dataService.updateUsersInCache(dataService.getAllUsers());
        log.info("Scheduled Update User Details completed successfully");
    }

    public void updateCoronaDataInCache() throws MalformedURLException {
        var start = System.currentTimeMillis();
        List<CoronaData> coronaDataList = dataService.readCSV(CONFIRMED_FILE_NAME, CONFIRMED);
        coronaDataList = dataService.editExistingList(coronaDataList, DEATH_FILE_NAME, DEATH);
        coronaDataList = dataService.editExistingList(coronaDataList, RECOVERED_FILE_NAME, RECOVERED);
        var localCache = LocalCache.getInstance();
        List<String> newlyAddedData = dataService.saveToCollection(coronaDataList);
        dataService.deleteFromCollection(localCache.getIdsOfMongoData());
        localCache.setIdsOfMongoData(newlyAddedData);
        localCache.setLastUpdatedMilliSeconds(System.currentTimeMillis());
        log.info("Scheduled Update Corona Data to Collection Successfully completed in " + (System.currentTimeMillis() - start) + "ms.");
    }

}

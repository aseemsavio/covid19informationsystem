package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.model.CoronaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.util.List;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.CONFIRMED_FILE_NAME;
import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.DEATH_FILE_NAME;
import static com.aseemsavio.covid19informationsystem.utils.Type.CONFIRMED;
import static com.aseemsavio.covid19informationsystem.utils.Type.DEATH;

/**
 * Dude, execute this code on startup.
 */

@Component
public class StartUpRituals {

    @Autowired
    CoronaDataService dataService;

    private static final Logger log = LoggerFactory.getLogger(CoronaDataService.class);

    @PostConstruct
    private void init() throws MalformedURLException {
        long start = System.currentTimeMillis();
        log.info("App Initiation Rituals starting... ");
        List<CoronaData> coronaDataList = dataService.readCSV(CONFIRMED_FILE_NAME, CONFIRMED);
        coronaDataList = dataService.editExistingList(coronaDataList, DEATH_FILE_NAME, DEATH);
        dataService.saveToCollection(coronaDataList);
        log.info("App Initiation Rituals Complete [" + (System.currentTimeMillis() - start) + "ms.] Ready to serve user requests.");
    }

}

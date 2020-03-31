package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class LocalCache {

    private static final Logger log = LoggerFactory.getLogger(LocalCache.class);
    private static LocalCache instance = null;

    private LocalCache() {
        log.info("Local Cache instantiated.");
    }

    public static LocalCache getInstance() {
        if (instance == null)
            instance = new LocalCache();
        return instance;
    }

    private Map<String, User> users;
    private List<String> idsOfMongoData;
    private List<String> dates;
    private long lastUpdatedMilliSeconds;

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public List<String> getIdsOfMongoData() {
        return idsOfMongoData;
    }

    public void setIdsOfMongoData(List<String> idsOfMongoData) {
        this.idsOfMongoData = idsOfMongoData;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public long getLastUpdatedMilliSeconds() {
        return lastUpdatedMilliSeconds;
    }

    public void setLastUpdatedMilliSeconds(long lastUpdatedMilliSeconds) {
        this.lastUpdatedMilliSeconds = lastUpdatedMilliSeconds;
    }
}

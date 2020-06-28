package com.aseemsavio.covid19informationsystem.controller;

import com.aseemsavio.covid19informationsystem.service.LocalCache;
import com.aseemsavio.covid19informationsystem.service.ScheduledJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;

@RestController
public class ScheduledJobsController {

    @Autowired
    ScheduledJobs scheduledJobs;

    /**
     * Every 30 minutes
     */
    @Scheduled(cron = CRON_EVERY_THIRTY_MINS)
    public void updateUserDetailsInCache() {
        var localCache = LocalCache.getInstance();
        scheduledJobs.updateUserDetailsInCache(localCache);
    }

    /**
     * Every 1 hour
     *
     * @throws MalformedURLException
     */
    @Scheduled(cron = CRON_EVERY_ONE_HOUR)
    public void updateCoronaDataToDatabase() throws MalformedURLException {
        var localCache = LocalCache.getInstance();
        scheduledJobs.updateCoronaDataInCache(localCache);
    }

    @GetMapping(HEARTBEAT_HEALTH_ENDPOINT)
    public String healthCheck() {
        return DUB_HEARTBEAT;
    }

}

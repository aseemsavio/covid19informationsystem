package com.aseemsavio.covid19informationsystem.controller;

import com.aseemsavio.covid19informationsystem.service.LocalCache;
import com.aseemsavio.covid19informationsystem.service.ScheduledJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class ScheduledJobsController {

    @Autowired
    ScheduledJobs scheduledJobs;

    /**
     * Every 30 minutes
     */
    @Scheduled(cron = "0 0/30 * * * *")
    public void updateUserDetailsInCache() {
        var localCache = LocalCache.getInstance();
        scheduledJobs.updateUserDetailsInCache(localCache);
    }

    /**
     * Every 1 hour
     *
     * @throws MalformedURLException
     */
    @Scheduled(cron = "0 0/59 * * * *")
    public void updateCoronaDataToDatabase() throws MalformedURLException {
        var localCache = LocalCache.getInstance();
        scheduledJobs.updateCoronaDataInCache(localCache);
    }

    @GetMapping("/all/lub")
    public String healthCheck() {
        return "dub";
    }

}

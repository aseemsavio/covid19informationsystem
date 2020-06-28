package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ScheduledJobsTest {

    @Mock
    CoronaDataService coronaDataService;

    @InjectMocks
    ScheduledJobs scheduledJobs;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateUserDetailsInCacheTest() {

        var localCache = LocalCache.getInstance();
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

        List<User> users = List.of(user, user2);

        Mockito.when(coronaDataService.getAllUsers()).thenReturn(users);
        scheduledJobs.updateUserDetailsInCache(localCache);
        System.out.println(localCache.getUsers());

    }

    @AfterEach
    public void tearDown() {
        scheduledJobs = null;
    }

}

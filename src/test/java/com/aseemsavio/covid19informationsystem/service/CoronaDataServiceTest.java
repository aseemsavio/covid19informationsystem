package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.model.User;
import com.aseemsavio.covid19informationsystem.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class CoronaDataServiceTest {

    @InjectMocks
    CoronaDataService coronaDataService;

    @Mock
    UserRepository userRepository;

    List<User> userList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsersTest() {
        giveUsers();
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<User> users = coronaDataService.getAllUsers();
        Assertions.assertEquals(users.size(), 2);
        Assertions.assertEquals(users.get(0).isActive(), true);
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

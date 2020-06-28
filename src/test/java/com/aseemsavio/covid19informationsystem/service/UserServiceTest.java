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

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.DUPLICATE_AUTH_KEY;
import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.TRY_AGAIN_LATER;

public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setFirstName("a");
        user.setLastName("a");
        user.setEmail("a");
        user.setCompany("a");
        user.setAuthorizationKey("a");
        user.setActive(true);
        user.setPlan(0);
    }

    @Test
    public void getAuthorizationCodeTestEmailAlreadyFound() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(new User());
        Assertions.assertEquals(userService.getAuthorizationCode(user), "Email already Found.");
    }

    @Test
    public void getAuthorizationCodeTestDuplicateAuthKey() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(userRepository.findByAuthorizationKey(Mockito.anyString())).thenReturn(new User());
        Assertions.assertEquals(userService.getAuthorizationCode(user), DUPLICATE_AUTH_KEY);
    }

    @Test
    public void getAuthorizationCodeTestSuccess() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(userRepository.findByAuthorizationKey(Mockito.anyString())).thenReturn(null);
        User savedUser = new User();
        savedUser.setAuthorizationKey("abc");
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(savedUser);
        Assertions.assertEquals(userService.getAuthorizationCode(user), "abc");
    }

    @Test
    public void getAuthorizationCodeTestSemiSuccess() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(userRepository.findByAuthorizationKey(Mockito.anyString())).thenReturn(null);
        User savedUser = new User();
        savedUser.setAuthorizationKey(null);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(savedUser);
        Assertions.assertEquals(userService.getAuthorizationCode(user), TRY_AGAIN_LATER);
    }

    @AfterEach
    public void tearDown() {
        user = null;
    }

}

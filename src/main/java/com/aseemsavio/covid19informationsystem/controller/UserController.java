package com.aseemsavio.covid19informationsystem.controller;

import com.aseemsavio.covid19informationsystem.model.User;
import com.aseemsavio.covid19informationsystem.model.UserRequest;
import com.aseemsavio.covid19informationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    //@PostMapping(value = "/getAuthorizationCode")
    public String getAuthorizationCode(@RequestBody UserRequest userRequest) {
        User user = new User();
        user.setActive(true);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setCompany(userRequest.getCompany());
        user.setPlan(0);
        user.setAuthorizationKey(UUID.randomUUID().toString());
        return userService.getAuthorizationCode(user);
    }

}

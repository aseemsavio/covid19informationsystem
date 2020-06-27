package com.aseemsavio.covid19informationsystem.controller;

import com.aseemsavio.covid19informationsystem.model.User;
import com.aseemsavio.covid19informationsystem.model.UserRequest;
import com.aseemsavio.covid19informationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;

@RestController
@RequestMapping(value = ADMIN)
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping(value = USER)
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

    @GetMapping(value = USERS)
    public List<User> getUsers() {
        return userService.getUsers();
    }


}

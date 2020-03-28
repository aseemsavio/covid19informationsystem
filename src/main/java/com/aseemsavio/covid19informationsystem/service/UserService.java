package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.model.User;
import com.aseemsavio.covid19informationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String getAuthorizationCode(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null)
            return "Email already Found.";
        if (userRepository.findByAuthorizationKey(user.getAuthorizationKey()) != null)
            return "Could not create your account now. Please try again after sometime.";
        User newUser = userRepository.save(user);
        try {
            if (newUser.getAuthorizationKey() != null)
                return "You're added to the FREE plan. You are permitted to send 30 requests per second to our servers. Enclosed within '<' and '>' is your Unique Authorization Code. Please store it in a secure location and keep it a secret. (The symbols are not part of your authorization code) <" + newUser.getAuthorizationKey() + ">.";
            else
                return "Couldn't complete your request. Please try again later.";
        } catch(Exception e) {
            return "Couldn't complete your request. Please try again later." + e;
        }
    }
}

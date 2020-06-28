package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.model.User;
import com.aseemsavio.covid19informationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String getAuthorizationCode(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null)
            return EMAIL_ALREADY_FOUND;
        if (userRepository.findByAuthorizationKey(user.getAuthorizationKey()) != null)
            return DUPLICATE_AUTH_KEY;
        User newUser = userRepository.save(user);
        try {
            if (newUser.getAuthorizationKey() != null)
                return newUser.getAuthorizationKey();
            else
                return TRY_AGAIN_LATER;
        } catch(Exception e) {
            return TRY_AGAIN_LATER + e;
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}

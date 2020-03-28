package com.aseemsavio.covid19informationsystem.repository;

import com.aseemsavio.covid19informationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAuthorizationKey(String authorizationKey);

    User findByEmail(String email);
}

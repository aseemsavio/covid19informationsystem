package com.aseemsavio.covid19informationsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.ACTUATOR_SECURITY;
import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.ADMIN_SECURITY;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${actuator.username}")
    private String username;

    @Value("${actuator.password}")
    private String password;

    //@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(ACTUATOR_SECURITY).authenticated()
                .antMatchers(ADMIN_SECURITY).authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(username).password("{noop}" + password).roles("USER");
    }


}

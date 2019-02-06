package com.okan.poetica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


    protected  void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email as principal, password as credentails, true from user where email=?")
                .authoritiesByUsernameQuery("select email as principal, role from user where email=?")
                .passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");
    }

    private PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    protected  void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers("/users","/user","/","/users/**","/createAdmin/**","/api/login/**","/api/user/**",
                        "/userbylikename/**","/userbyemail/**","/userbyusername/**","/contents/**","/content/**","/contentByCreator/**")
                .permitAll().anyRequest().authenticated()
                .and().httpBasic();

    }
}

package org.bbojk.sideprojectplatformbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@Profile("test")
public class TestUserDetailsConfig {

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        List<UserDetails> testUsers = List.of(
                User.builder().username("a").password("{noop}1234").build(),
                User.builder().username("b").password("{noop}1234").build(),
                User.builder().username("c").password("{noop}1234").build()

        );

        return new InMemoryUserDetailsManager(testUsers);
    }
}

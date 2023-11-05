package org.bbojk.sideprojectplatformbackend.user.auth;

import org.assertj.core.api.Assertions;
import org.bbojk.sideprojectplatformbackend.user.auth.dto.AuthenticationRequest;
import org.bbojk.sideprojectplatformbackend.user.auth.dto.RegisterRequest;
import org.bbojk.sideprojectplatformbackend.user.domain.AppUser;
import org.bbojk.sideprojectplatformbackend.user.domain.AppUserMemRepository;
import org.bbojk.sideprojectplatformbackend.user.domain.AppUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    private void afterEach(){
        if(appUserRepository instanceof AppUserMemRepository){
            ((AppUserMemRepository) appUserRepository).clear();
        }
    }

    @Test
    void register() {

        //given
        RegisterRequest request = RegisterRequest.builder()
                .email("test@test.com")
                .password("test1234")
                .build();

        //when
        String token = authenticationService.register(request).getToken();
        System.out.println(token); //로그로 변경하는게 좋을듯

        //then
        AppUser findRequest = appUserRepository.findByEmail("test@test.com").orElseThrow();
        assertThat(request.getEmail()).isEqualTo(findRequest.getEmail());
        assertThat(passwordEncoder.matches(request.getPassword(), findRequest.getPassword())).isTrue();
    }

    @Test
    void authenticate() {
        //given
        RegisterRequest request = RegisterRequest.builder()
                .email("test@test.com")
                .password("test1234")
                .build();

        authenticationService.register(request);

        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("test@test.com")
                .password("test1234")
                .build();

        //when
        authenticationService.authenticate(authenticationRequest);

        //then
        assertThatNoException();


    }

    @Test
    void authentication_failed() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("test@test.com")
                .password("test1234")
                .build();

        //when
        assertThatThrownBy( () -> authenticationService.authenticate(authenticationRequest))
                .isInstanceOf(BadCredentialsException.class);

    }


}
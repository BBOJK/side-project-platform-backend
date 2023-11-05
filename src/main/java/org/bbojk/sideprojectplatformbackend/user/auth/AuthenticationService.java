package org.bbojk.sideprojectplatformbackend.user.auth;

import lombok.RequiredArgsConstructor;
import org.bbojk.sideprojectplatformbackend.security.JwtService;
import org.bbojk.sideprojectplatformbackend.user.auth.dto.AuthenticationRequest;
import org.bbojk.sideprojectplatformbackend.user.auth.dto.AuthenticationResponse;
import org.bbojk.sideprojectplatformbackend.user.auth.dto.RegisterRequest;
import org.bbojk.sideprojectplatformbackend.user.domain.AppUser;
import org.bbojk.sideprojectplatformbackend.user.domain.AppUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = AppUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("USER"))
                .build();
        repository.save(user);
        return getAuthenticationResponse(userDetailsService.loadUserByUsername(user.getEmail()));
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getEmail(),
                       request.getPassword()
               )
       );
       var user = repository.findByEmail(request.getEmail())
               .orElseThrow();

        return getAuthenticationResponse(userDetailsService.loadUserByUsername(user.getEmail()));
    }

    private AuthenticationResponse getAuthenticationResponse(UserDetails user) {
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

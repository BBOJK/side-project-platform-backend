package org.bbojk.sideprojectplatformbackend.security;

import lombok.RequiredArgsConstructor;
import org.bbojk.sideprojectplatformbackend.user.domain.AppUser;
import org.bbojk.sideprojectplatformbackend.user.domain.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user of email " + email + " not found."));
        return new AppUserDetails(user);
    }
}

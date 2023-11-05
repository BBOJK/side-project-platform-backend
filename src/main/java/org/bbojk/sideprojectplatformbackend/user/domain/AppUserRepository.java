package org.bbojk.sideprojectplatformbackend.user.domain;

import java.util.Optional;

public interface AppUserRepository {
    Optional<AppUser> findByEmail(String email);
    void save(AppUser appUser);
}

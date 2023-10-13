package org.bbojk.sideprojectplatformbackend.domain;

import java.util.Optional;

public interface AppUserRepository {
    Optional<AppUser> findByEmail(String email);
}

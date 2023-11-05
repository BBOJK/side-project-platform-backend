package org.bbojk.sideprojectplatformbackend.user.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AppUserMemRepository implements AppUserRepository {

    private static Long count = 0L;
    private static List<AppUser> appUsers = new ArrayList<>();

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return appUsers.stream()
                .filter(appUser ->
                        appUser.getEmail().equals(email)
                ).findFirst();
    }

    @Override
    public AppUser save(AppUser appUser) {
        appUser.setId(count++);
        appUsers.add(appUser);
        return appUser;
    }

    public void clear(){
        appUsers.clear();
    }
}

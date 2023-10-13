package org.bbojk.sideprojectplatformbackend.domain;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AppUser {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private List<String> roles;
}

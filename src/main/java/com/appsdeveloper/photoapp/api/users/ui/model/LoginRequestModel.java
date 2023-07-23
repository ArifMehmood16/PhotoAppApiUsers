package com.appsdeveloper.photoapp.api.users.ui.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestModel {
    private String email;
    private String password;
}

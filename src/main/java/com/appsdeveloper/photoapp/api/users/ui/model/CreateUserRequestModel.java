package com.appsdeveloper.photoapp.api.users.ui.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestModel {
    @NotNull(message = "Firstname cannot be null")
    @Size(min = 2, message = "Firstname should be more than 2 characters")
    private String firstName;
    @NotNull(message = "Lastname cannot be null")
    @Size(min = 2, message = "Lastname should be more than 2 characters")
    private String lastName;
    @NotNull(message = "Firstname cannot be null")
    @Email(message = "Email should be a valid format")
    private String email;
    @NotNull(message = "Password cannot be null")
    private String password;
}

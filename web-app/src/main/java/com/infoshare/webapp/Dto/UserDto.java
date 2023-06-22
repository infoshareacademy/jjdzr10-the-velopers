package com.infoshare.webapp.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
public class UserDto {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @Email
    private String email;
}

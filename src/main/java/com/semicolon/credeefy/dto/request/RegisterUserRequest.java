package com.semicolon.credeefy.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semicolon.credeefy.data.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;
}

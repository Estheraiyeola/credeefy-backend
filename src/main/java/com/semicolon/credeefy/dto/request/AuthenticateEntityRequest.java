package com.semicolon.credeefy.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateEntityRequest {
    private String email;
    private String password;
}

package com.semicolon.credeefy.data.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class AuthenticationData {
    private String accessToken;
    private HttpStatus status;
}

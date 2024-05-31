package com.semicolon.credeefy.dto.response;

import com.semicolon.credeefy.data.model.AuthenticationData;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticatedUserResponse {
    private AuthenticationData data;
    private String message;
}

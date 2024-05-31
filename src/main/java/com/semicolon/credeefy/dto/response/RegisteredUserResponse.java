package com.semicolon.credeefy.dto.response;

import com.semicolon.credeefy.data.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredUserResponse {
    private String message;
    private User registeredUser;
}

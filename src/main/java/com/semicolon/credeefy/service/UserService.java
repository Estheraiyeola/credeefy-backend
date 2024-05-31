package com.semicolon.credeefy.service;

import com.semicolon.credeefy.dto.request.AuthenticateEntityRequest;
import com.semicolon.credeefy.dto.request.RegisterUserRequest;
import com.semicolon.credeefy.dto.response.AuthenticatedEntityResponse;
import com.semicolon.credeefy.dto.response.RegisteredUserResponse;

public interface UserService {
    RegisteredUserResponse registerUser(RegisterUserRequest registerUserRequest);

    void deleteAll();

    AuthenticatedEntityResponse authenticateUser(AuthenticateEntityRequest authenticateEntityRequest);
}

package com.semicolon.credeefy.service;

import com.semicolon.credeefy.dto.request.AuthenticateEntityRequest;
import com.semicolon.credeefy.dto.request.RegisterUserRequest;
import com.semicolon.credeefy.dto.response.AuthenticatedEntityResponse;
import com.semicolon.credeefy.dto.response.RegisteredUserResponse;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.semicolon.credeefy.data.model.Role.STUDENT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @BeforeEach
    public void setUserUp(){
        userService.deleteAll();
    }

    @Test
    public void testThatUserCanBeRegistered(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Esther");
        registerUserRequest.setLastName("Aiyeola");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword(STUDENT.toString());
        registerUserRequest.setPassword("password");

        RegisteredUserResponse registeredUserResponse = userService.registerUser(registerUserRequest);

        assertThat(registeredUserResponse.getRegisteredUser().getFirstName(), is("Esther"));
    }
    @Test
    public void testThatUniqueUsersCanBeRegistered(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Esther");
        registerUserRequest.setLastName("Aiyeola");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setRole(STUDENT);
        registerUserRequest.setPassword("password");

        RegisteredUserResponse registeredUserResponse = userService.registerUser(registerUserRequest);
        assertThat(registeredUserResponse.getRegisteredUser().getFirstName(), is("Esther"));
        assertThrows(EntityExistsException.class, ()-> userService.registerUser(registerUserRequest));
    }
    @Test
    public void testThatUserCanBeAuthenticated() {
        RegisterUserRequest createUserRequest = new RegisterUserRequest();
        createUserRequest.setFirstName("Esther");
        createUserRequest.setLastName("Aiyeola");
        createUserRequest.setEmail("estheraiyeola@yahoo.com");
        createUserRequest.setPassword("password");
        createUserRequest.setRole(STUDENT);

        RegisteredUserResponse createdUserResponse = userService.registerUser(createUserRequest);
        assertThat(createdUserResponse.getRegisteredUser().getFirstName(), is("Esther"));

        AuthenticateEntityRequest authenticateEntityRequest = new AuthenticateEntityRequest();
        authenticateEntityRequest.setEmail("estheraiyeola@yahoo.com");
        authenticateEntityRequest.setPassword("password");

        AuthenticatedEntityResponse response = userService.authenticateUser(authenticateEntityRequest);
        assertThat(response.getMessage(), is("Successfully Authenticated"));
    }
}

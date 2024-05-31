package com.semicolon.credeefy.service;

import com.semicolon.credeefy.dto.request.AuthenticateEntityRequest;
import com.semicolon.credeefy.dto.request.RegisterInstitutionRequest;
import com.semicolon.credeefy.dto.response.AuthenticatedEntityResponse;
import com.semicolon.credeefy.dto.response.RegisteredInstitutionResponse;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class InstitutionServiceTest {
    @Autowired
    private InstitutionService institutionService;
    @BeforeEach
    public void setUserUp(){
        institutionService.deleteAll();
    }

    @Test
    public void testThatAnInstitutionCanBeRegistered(){
        RegisterInstitutionRequest registerInstitutionRequest = new RegisterInstitutionRequest();
        registerInstitutionRequest.setName("Semicolon Africa");
        registerInstitutionRequest.setLocation("Sabo, Yaba");
        registerInstitutionRequest.setPassword("password");
        registerInstitutionRequest.setEmail("semicolon.africa@gmail.com");


        RegisteredInstitutionResponse registeredInstitutionResponse = institutionService.registerInstitution(registerInstitutionRequest);

        assertThat(registeredInstitutionResponse.getRegisteredInstitution().getName(), is("Semicolon Africa"));
    }

    @Test
    public void testThatUniqueInstitutionsCanBeRegistered(){
        RegisterInstitutionRequest registerInstitutionRequest = new RegisterInstitutionRequest();
        registerInstitutionRequest.setName("Semicolon Africa");
        registerInstitutionRequest.setLocation("Sabo, Yaba");
        registerInstitutionRequest.setPassword("password");
        registerInstitutionRequest.setEmail("semicolon.africa@gmail.com");



        RegisteredInstitutionResponse registeredInstitutionResponse = institutionService.registerInstitution(registerInstitutionRequest);
        assertThat(registeredInstitutionResponse.getRegisteredInstitution().getName(), is("Semicolon Africa"));

        assertThrows(EntityExistsException.class, ()->institutionService.registerInstitution(registerInstitutionRequest));
    }

    @Test
    public void testThatInstitutionsCanBeAuthenticated(){
        RegisterInstitutionRequest registerInstitutionRequest = new RegisterInstitutionRequest();
        registerInstitutionRequest.setName("Semicolon Africa");
        registerInstitutionRequest.setLocation("Sabo, Yaba");
        registerInstitutionRequest.setPassword("password");
        registerInstitutionRequest.setEmail("semicolon.africa@gmail.com");



        RegisteredInstitutionResponse registeredInstitutionResponse = institutionService.registerInstitution(registerInstitutionRequest);
        assertThat(registeredInstitutionResponse.getRegisteredInstitution().getName(), is("Semicolon Africa"));


        AuthenticateEntityRequest authenticateEntityRequest = new AuthenticateEntityRequest();
        authenticateEntityRequest.setEmail("semicolon.africa@gmail.com");
        authenticateEntityRequest.setPassword("password");

        AuthenticatedEntityResponse response = institutionService.authenticateUser(authenticateEntityRequest);
        assertThat(response.getMessage(), is("Successfully Authenticated"));
    }
}

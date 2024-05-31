package com.semicolon.credeefy.service;

import com.semicolon.credeefy.dto.request.AuthenticateEntityRequest;
import com.semicolon.credeefy.dto.request.RegisterInstitutionRequest;
import com.semicolon.credeefy.dto.response.AuthenticatedEntityResponse;
import com.semicolon.credeefy.dto.response.RegisteredInstitutionResponse;

public interface InstitutionService {
    RegisteredInstitutionResponse registerInstitution(RegisterInstitutionRequest registerInstitutionRequest);

    void deleteAll();

    AuthenticatedEntityResponse authenticateUser(AuthenticateEntityRequest authenticateEntityRequest);
}

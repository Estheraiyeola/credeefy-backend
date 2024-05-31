package com.semicolon.credeefy.dto.response;

import com.semicolon.credeefy.data.model.Institution;
import com.semicolon.credeefy.data.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredInstitutionResponse {
    private String message;
    private Institution registeredInstitution;
}

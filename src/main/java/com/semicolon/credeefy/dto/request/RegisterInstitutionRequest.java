package com.semicolon.credeefy.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterInstitutionRequest {
    private String name;
    private String location;
    private String password;
    private String email;
}

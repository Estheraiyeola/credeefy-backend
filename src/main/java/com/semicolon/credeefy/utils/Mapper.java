package com.semicolon.credeefy.utils;

import com.semicolon.credeefy.data.model.Institution;
import com.semicolon.credeefy.data.model.User;
import com.semicolon.credeefy.dto.request.RegisterInstitutionRequest;
import com.semicolon.credeefy.dto.request.RegisterUserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public static User map(RegisterUserRequest request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        return user;
    }

    public static Institution map(RegisterInstitutionRequest request){
        Institution institution = new Institution();
        institution.setName(request.getName());
        institution.setLocation(request.getLocation());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        institution.setPassword(encodedPassword);
        institution.setEmail(request.getEmail());
        return institution;
    }
}

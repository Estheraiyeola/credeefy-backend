package com.semicolon.credeefy.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.semicolon.credeefy.data.model.AuthenticationData;
import com.semicolon.credeefy.data.model.Institution;
import com.semicolon.credeefy.data.model.User;
import com.semicolon.credeefy.data.repository.InstitutionRepository;
import com.semicolon.credeefy.dto.request.AuthenticateEntityRequest;
import com.semicolon.credeefy.dto.request.RegisterInstitutionRequest;
import com.semicolon.credeefy.dto.response.AuthenticatedEntityResponse;
import com.semicolon.credeefy.dto.response.RegisteredInstitutionResponse;
import com.semicolon.credeefy.dto.response.RegisteredUserResponse;
import com.semicolon.credeefy.utils.SecretKeyGenerator;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.semicolon.credeefy.utils.Mapper.map;

@Service
@AllArgsConstructor
public class InstitutionServiceImpl implements InstitutionService{
    private final InstitutionRepository institutionRepository;
    @Override
    public RegisteredInstitutionResponse registerInstitution(RegisterInstitutionRequest registerInstitutionRequest) {
        Institution foundInstitution = institutionRepository.findInstitutionByEmail(registerInstitutionRequest.getEmail());
        if (foundInstitution == null){
            Institution mappedInstitution = map(registerInstitutionRequest);
            Institution savedInstitution = institutionRepository.save(mappedInstitution);

            RegisteredInstitutionResponse response = new RegisteredInstitutionResponse();
            response.setMessage("Institution Registered Successfully");
            response.setRegisteredInstitution(savedInstitution);
            return response;
        }
        throw new EntityExistsException("Institution Already Exists");
    }

    @Override
    public void deleteAll() {
        institutionRepository.deleteAll();
    }

    @Override
    public AuthenticatedEntityResponse authenticateUser(AuthenticateEntityRequest authenticateEntityRequest) {
        Institution foundInstitution = institutionRepository.findInstitutionByEmail(authenticateEntityRequest.getEmail());
        if (foundInstitution != null) {
            boolean isUser = BCrypt.checkpw(authenticateEntityRequest.getPassword(), foundInstitution.getPassword());
            if (isUser){
                SecretKeyGenerator secretKeyGenerator = new SecretKeyGenerator();
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime expirationTime = currentTime.plusHours(1);
                Date expiryDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());

                byte[] keyBytes = secretKeyGenerator.generateSecureRandomBytes();
                Algorithm algorithm = Algorithm.HMAC256(keyBytes);

                String accessToken = JWT.create()
                        .withClaim("id", foundInstitution.getInstitutionId())
                        .withClaim("firstName", foundInstitution.getName())
                        .withClaim("lastName", foundInstitution.getLocation())
                        .withClaim("email", foundInstitution.getEmail())
                        .withIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                        .withExpiresAt(expiryDate)
                        .sign(algorithm);
                AuthenticatedEntityResponse response = new AuthenticatedEntityResponse();

                AuthenticationData data = new AuthenticationData();

                data.setAccessToken(accessToken);
                data.setStatus(HttpStatus.ACCEPTED);

                response.setData(data);
                response.setMessage("Successfully Authenticated");
                return response;
            }
        }
        throw new EntityNotFoundException("Institution Does Not Exist");
    }
}

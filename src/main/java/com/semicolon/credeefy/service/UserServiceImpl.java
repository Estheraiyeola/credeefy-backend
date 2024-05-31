package com.semicolon.credeefy.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.semicolon.credeefy.data.model.AuthenticationData;
import com.semicolon.credeefy.data.model.User;
import com.semicolon.credeefy.data.repository.UserRepository;
import com.semicolon.credeefy.dto.request.AuthenticateEntityRequest;
import com.semicolon.credeefy.dto.request.RegisterUserRequest;
import com.semicolon.credeefy.dto.response.AuthenticatedEntityResponse;
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
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public RegisteredUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        User foundUser = userRepository.findUserByEmail(registerUserRequest.getEmail());
        if (foundUser == null){
            User mappedUser = map(registerUserRequest);
            User savedUser = userRepository.save(mappedUser);

            RegisteredUserResponse response = new RegisteredUserResponse();
            response.setMessage("User Registered Successfully");
            response.setRegisteredUser(savedUser);
            return response;
        }
        throw new EntityExistsException("User Already Exists");
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public AuthenticatedEntityResponse authenticateUser(AuthenticateEntityRequest authenticateEntityRequest) {
        User foundUser = userRepository.findUserByEmail(authenticateEntityRequest.getEmail());
        if (foundUser!= null) {
            boolean isUser = BCrypt.checkpw(authenticateEntityRequest.getPassword(), foundUser.getPassword());
            if (isUser){
                SecretKeyGenerator secretKeyGenerator = new SecretKeyGenerator();
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime expirationTime = currentTime.plusHours(1);
                Date expiryDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());

                byte[] keyBytes = secretKeyGenerator.generateSecureRandomBytes();
                Algorithm algorithm = Algorithm.HMAC256(keyBytes);

                String accessToken = JWT.create()
                        .withClaim("id", foundUser.getId())
                        .withClaim("firstName", foundUser.getFirstName())
                        .withClaim("lastName", foundUser.getLastName())
                        .withClaim("email", foundUser.getEmail())
                        .withClaim("role", foundUser.getRole().toString())
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
        throw new EntityNotFoundException("User Does Not Exist");
    }
}

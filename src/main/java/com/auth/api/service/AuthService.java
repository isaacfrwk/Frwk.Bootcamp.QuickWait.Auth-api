package com.auth.api.service;

import com.auth.api.dto.request.LoginRequest;
import com.auth.api.dto.request.SignupRequest;
import com.auth.api.dto.response.Response;
import com.auth.api.entity.UserSystem;
import com.auth.api.repository.UserSystemRepository;
import com.auth.api.security.jwt.JwtUtils;
import com.auth.api.security.services.UserDetailsImpl;
import com.auth.api.validation.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserSystemRepository userRepository;
    private PasswordEncoder encoder;
    private JwtUtils jwtUtils;

    public ResponseEntity<Response> authenticateUser(@Valid LoginRequest loginRequest) {
        UserValidation.loginValidation(loginRequest, existsByUsername(loginRequest.getUsername()));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new Response.JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(), HttpStatus.OK.value()));
    }

    public ResponseEntity<Response> registerUser(@Valid SignupRequest signUpRequest) {
        UserValidation.signupValidation(signUpRequest,
                                        existsByUsername(signUpRequest.getUsername()),
                                        existsByEmail(signUpRequest.getEmail()),
                                signUpRequest.getCpf() != null && existsByCpf(signUpRequest.getCpf()));

        UserSystem user = new UserSystem(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getCpf(), signUpRequest.getPhoneNumber(), signUpRequest.getBirthDate());

        userRepository.save(user);

        return ResponseEntity.ok(new Response.MessageResponse("User registered successfully!", HttpStatus.OK.value()));
    }

    private boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean existsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }
}

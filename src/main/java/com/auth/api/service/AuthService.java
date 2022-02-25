package com.auth.api.service;

import com.auth.api.dto.request.LoginRequest;
import com.auth.api.dto.request.SignupRequest;
import com.auth.api.dto.response.Response;
import com.auth.api.entity.UserSystem;
import com.auth.api.repository.UserSystemRepository;
import com.auth.api.security.jwt.JwtUtils;
import com.auth.api.security.services.UserDetailsImpl;
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
        if (!userRepository.existsByUsername(loginRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response.MessageResponse("Error: Username does not exist!", HttpStatus.BAD_REQUEST.value()));
        }

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
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response.MessageResponse("Error: Username is already taken!", HttpStatus.BAD_REQUEST.value()));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response.MessageResponse("Error: Email is already in use!", HttpStatus.BAD_REQUEST.value()));
        }

        if (signUpRequest.getCpf() != null && userRepository.existsByCpf(signUpRequest.getCpf())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response.MessageResponse("Error: CPF is already in use!", HttpStatus.BAD_REQUEST.value()));
        }

        UserSystem user = new UserSystem(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getCpf(), signUpRequest.getPhoneNumber(), signUpRequest.getBirthDate());

        userRepository.save(user);

        return ResponseEntity.ok(new Response.MessageResponse("User registered successfully!", HttpStatus.OK.value()));
    }

}

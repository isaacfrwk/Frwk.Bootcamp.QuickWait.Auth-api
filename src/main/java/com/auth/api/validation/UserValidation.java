package com.auth.api.validation;

import com.auth.api.dto.request.LoginRequest;
import com.auth.api.dto.request.SignupRequest;
import com.auth.api.validation.exception.ValidationException;

import static org.springframework.util.ObjectUtils.isEmpty;

public class UserValidation {

    public static void loginValidation(LoginRequest request, boolean userExists) {
        if (isEmpty(request.getUsername())) {
            throw new ValidationException("The username was not informed.");
        }

        if (!userExists) {
            throw new ValidationException("Error: The username informed does not exist!");
        }

        if (isEmpty(request.getPassword())) {
            throw new ValidationException("The password was not informed.");
        }
    }

    public static void signupValidation(SignupRequest request, boolean userExists, boolean emailExists, boolean cpfExists) {
        if (isEmpty(request.getUsername())) {
            throw new ValidationException("The username was not informed.");
        }

        if (isEmpty(request.getEmail())) {
            throw new ValidationException("The email was not informed.");
        }

        if (isEmpty(request.getPassword())) {
            throw new ValidationException("The password was not informed.");
        }

        if (userExists) {
            throw new ValidationException("This username is already being used.");
        }

        if (emailExists) {
            throw new ValidationException("This email is already being used.");
        }

        if (cpfExists) {
            throw new ValidationException("This cpf is already being used.");
        }
    }
}

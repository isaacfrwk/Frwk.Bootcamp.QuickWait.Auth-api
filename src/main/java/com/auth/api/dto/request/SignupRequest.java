package com.auth.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @Size(min = 11, max = 11)
    private String cpf;

    @JsonProperty("phone_number")
    @Size(min = 11, max = 11)
    private String phoneNumber;

    @JsonProperty("birth_date")
    @Size(min = 8, max = 8)
    private String birthDate;

}
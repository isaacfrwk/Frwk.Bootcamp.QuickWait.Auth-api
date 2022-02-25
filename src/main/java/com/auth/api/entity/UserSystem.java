package com.auth.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class UserSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    @Size(min = 4, max = 20)
    private String username;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @Size(min = 15, max = 50)
    @Email
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    @Size(max = 120)
    private String password;

    @Column(name = "CPF", unique = true)
    @Size(min = 11, max = 11)
    private String cpf;

    @Column(name = "PHONE_NUMBER")
    @Size(min = 8, max = 11)
    private String phoneNumber;

    @Column(name = "BIRTH_DATE")
    @Size(min = 8, max = 8)
    private String birthDate;

    public UserSystem(String username, String email, String password, String cpf, String phoneNumber, String birthDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }
}
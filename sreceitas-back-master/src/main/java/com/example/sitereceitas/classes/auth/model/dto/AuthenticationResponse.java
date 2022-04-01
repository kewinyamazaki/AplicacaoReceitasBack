package com.example.sitereceitas.classes.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

    private String autenticacaoToken;
    private String refreshToken;
    private Instant expiraEm;
    private String email;
    private String nome;
}

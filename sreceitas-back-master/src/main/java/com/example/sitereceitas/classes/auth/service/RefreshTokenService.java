package com.example.sitereceitas.classes.auth.service;

import com.example.sitereceitas.classes.auth.model.common.RefreshToken;
import com.example.sitereceitas.classes.auth.repository.SRRefreshTokenRepository;
import com.example.sitereceitas.exceptions.NegocioException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private SRRefreshTokenRepository refreshTokenRepository;

    public RefreshToken gerarRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setDataCriacao(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }

    void validarRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new NegocioException("Refresh Token inválido"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}

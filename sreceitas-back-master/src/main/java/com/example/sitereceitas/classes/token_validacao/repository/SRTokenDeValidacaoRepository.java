package com.example.sitereceitas.classes.token_validacao.repository;

import com.example.sitereceitas.classes.token_validacao.model.common.TokenDeValidacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface SRTokenDeValidacaoRepository extends JpaRepository<TokenDeValidacao, Long> {
    Optional<TokenDeValidacao> findByToken(String token);
}

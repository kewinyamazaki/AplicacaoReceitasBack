package com.example.sitereceitas.classes.ingrediente.repository;

import com.example.sitereceitas.classes.ingrediente.model.common.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface SRIngredienteRepository extends JpaRepository<Ingrediente, Long>{

    Optional<Ingrediente> findByNome(String nome);
}
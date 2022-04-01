package com.example.sitereceitas.classes.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.sitereceitas.classes.usuario.model.common.Usuario;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface SRUsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);
}

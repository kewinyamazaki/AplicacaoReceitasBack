package com.example.sitereceitas.classes.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.sitereceitas.classes.usuario.model.common.Usuario;

import java.util.Optional;

public interface SRUsuarioAuthRepository extends CrudRepository<Usuario, Long>{
	Optional<Usuario> findByEmail(String email);
}

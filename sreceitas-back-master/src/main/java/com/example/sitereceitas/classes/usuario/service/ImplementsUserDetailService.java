package com.example.sitereceitas.classes.usuario.service;

import com.example.sitereceitas.classes.usuario.repository.SRUsuarioRepository;
import com.example.sitereceitas.util.MensagensUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.sitereceitas.classes.usuario.model.common.Usuario;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImplementsUserDetailService implements UserDetailsService{

	private final SRUsuarioRepository usuarioAuthRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) {
		Optional<Usuario> usuarioOptional = usuarioAuthRepository.findByEmail(email);
		Usuario usuario = usuarioOptional
				.orElseThrow(() -> new UsernameNotFoundException(MensagensUtil.MSG_USUARIO_INVALIDO_AUTH));
		return new org.springframework.security.core.userdetails.User(
				usuario.getEmail(),
				usuario.getSenha(),
				usuario.getEmailValidado(),
				true, true, true,
				getAuthorities("USER")
		);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role){
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}
}


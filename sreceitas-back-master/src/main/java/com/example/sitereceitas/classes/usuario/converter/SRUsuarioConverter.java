package com.example.sitereceitas.classes.usuario.converter;

import org.springframework.stereotype.Component;

import com.example.sitereceitas.classes.usuario.model.dto.UsuarioDTO;
import com.example.sitereceitas.classes.usuario.model.common.Usuario;

import java.util.ArrayList;
import java.util.List;

@Component
public class SRUsuarioConverter {

	public UsuarioDTO EntityToDTO(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setUserId(usuario.getUserId());
		usuarioDTO.setNome(usuario.getNome());
		usuarioDTO.setSenha(usuario.getSenha());
		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setEmailValidado(usuario.getEmailValidado());
		usuarioDTO.setEndereco(usuario.getEndereco());
		usuarioDTO.setDataNascimento(usuario.getDataNascimento());
		return usuarioDTO;
	}
	
	public Usuario DTOToEntity(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		usuario.setUserId(usuarioDTO.getUserId());
		usuario.setNome(usuarioDTO.getNome());
		usuario.setSenha(usuarioDTO.getSenha());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setEmailValidado(usuario.getEmailValidado());
		usuario.setEndereco(usuarioDTO.getEndereco());
		usuario.setDataNascimento(usuarioDTO.getDataNascimento());
		return usuario;
	}

	public List<UsuarioDTO> ListToEntityToDto(List<Usuario> lista) {
		List<UsuarioDTO> listaDto = new ArrayList<>();
		for (Usuario usuario : lista) {
			listaDto.add(EntityToDTO(usuario));
		}

		return listaDto;
	}

	public UsuarioDTO EntityToDTOPerfil(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setNome(usuario.getNome());
		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setEndereco(usuario.getEndereco());
		usuarioDTO.setDataNascimento(usuario.getDataNascimento());
		return usuarioDTO;
	}
}

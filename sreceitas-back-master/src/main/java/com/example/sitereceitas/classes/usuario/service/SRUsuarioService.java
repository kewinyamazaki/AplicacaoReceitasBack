package com.example.sitereceitas.classes.usuario.service;

import com.example.sitereceitas.classes.auth.service.AuthService;
import com.example.sitereceitas.classes.usuario.converter.SRUsuarioConverter;
import com.example.sitereceitas.classes.usuario.model.common.Usuario;
import com.example.sitereceitas.classes.usuario.model.dto.UsuarioDTO;
import com.example.sitereceitas.classes.usuario.repository.SRUsuarioRepository;
import com.example.sitereceitas.exceptions.NegocioException;
import com.example.sitereceitas.util.MensagensUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SRUsuarioService {

	private final SRUsuarioRepository usuarioRepository;
	private final SRUsuarioConverter usuarioConverter;
	private final AuthService authService;

	@Transactional
	public UsuarioDTO alterar(UsuarioDTO dto){
		Usuario usuario = usuarioConverter.DTOToEntity(dto);
		usuarioRepository.save(usuario);
		return usuarioConverter.EntityToDTO(usuario);
	}

	@Transactional
	public Long deletar(Long id){
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if(!usuario.isPresent())
			throw new NegocioException(MensagensUtil.MSG_USUARIO_INVALIDO);
		usuarioRepository.delete(usuario.get());
		return id;
	}

	@Transactional
	public UsuarioDTO consultarLogado(){
		Usuario usuario = authService.obterUsuarioAutenticado();
		return usuarioConverter.EntityToDTO(usuario);
	}

	@Transactional
	public List<UsuarioDTO> listarTodos(){
			List<Usuario> usuarios = usuarioRepository.findAll();
			if(usuarios.isEmpty())
				throw new NegocioException(MensagensUtil.MSG_NENHUM_REGISTRO);
			return usuarioConverter.ListToEntityToDto(usuarios);
	}
}

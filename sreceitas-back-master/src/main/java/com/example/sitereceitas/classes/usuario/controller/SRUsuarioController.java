package com.example.sitereceitas.classes.usuario.controller;

import com.example.sitereceitas.classes.auth.model.dto.AuthenticationResponse;
import com.example.sitereceitas.classes.auth.model.dto.LoginRequest;
import com.example.sitereceitas.classes.auth.service.AuthService;
import com.example.sitereceitas.classes.usuario.model.dto.UsuarioDTO;
import com.example.sitereceitas.classes.usuario.service.SRUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/v1")
@AllArgsConstructor
public class SRUsuarioController {

	private final AuthService authService;
	private final SRUsuarioService usuarioService;

	/* ---------- Métodos relacionados às Receitas ---------- */

	// Entrar (Login)
	@PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthenticationResponse logar(@RequestBody LoginRequest login) {
		return authService.login(login);
	}

	// Cadastrar
	@PostMapping(value="/cadastrar",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioDTO dto) {
		return ResponseEntity.ok(authService.cadastrar(dto));
	}

	// Verificar Email
	@GetMapping(value="/verificar/{token}")
	public ResponseEntity<String> verificarConta(@PathVariable String token){
		authService.verificarConta(token);
		return new ResponseEntity<>("Conta ativada com sucesso", HttpStatus.OK);
	}

	// Alterar
	@PutMapping(value="/alterar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDTO> alterar(@RequestBody UsuarioDTO dto) {
		return ResponseEntity.ok(usuarioService.alterar(dto));
	}

	// Consultar
	@GetMapping(value="/conta")
	public ResponseEntity<UsuarioDTO> consultarLogado() {
		return ResponseEntity.ok(usuarioService.consultarLogado());
	}

	// Remover
	@DeleteMapping(value="/{userId}")
	public ResponseEntity<Long> deletar(@PathVariable(value = "userId") Long id) {
		return ResponseEntity.ok(usuarioService.deletar(id));
	}

	// Listar
	@GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UsuarioDTO>> listarTodos() {
		return ResponseEntity.ok(usuarioService.listarTodos());
	}
}


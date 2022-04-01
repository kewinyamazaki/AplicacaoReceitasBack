package com.example.sitereceitas.classes.usuario.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UsuarioDTO {
	
	private Long userId;
	private String nome;
	private String email;
	private Boolean emailValidado;
	private String senha;
	private Date dataNascimento;
	private String endereco;
}

package com.example.sitereceitas.classes.auth.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	
	@NotNull(message="O email deve ser informado!")
	@NotEmpty
	private String email;
	
	@NotNull(message="A senha deve ser informada!")
	@NotEmpty
	private String senha;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}

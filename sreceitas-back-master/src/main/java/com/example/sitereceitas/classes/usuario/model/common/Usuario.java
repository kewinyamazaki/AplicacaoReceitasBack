package com.example.sitereceitas.classes.usuario.model.common;

import com.example.sitereceitas.classes.receita.model.common.Receita;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="tb_usuario")
public class Usuario implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idusuario")
	private Long userId;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(
			name = "tb_favorito",
			joinColumns = { @JoinColumn(name = "idusuario") },
			inverseJoinColumns = { @JoinColumn(name = "idreceita") }
	)
	Set<Receita> favoritos = new HashSet<>();
	
	@NotNull
	@NotEmpty
	@Column(name = "nome")
	private String nome;
	
	@Column(name="email", unique=true)
	@NotNull
	@NotEmpty
	private String email;

	@Column(name="email_validado")
	@NotNull
	private Boolean emailValidado;
	
	@NotNull
	@NotEmpty
	@Column(name = "senha")
	private String senha;
	
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/YYYY")
	@Column(name="data_nascimento")
	private Date dataNascimento;
	
	@Column(name="endereco")
	private String endereco;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
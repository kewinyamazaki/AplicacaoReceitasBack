package com.example.sitereceitas.classes.receita.model.common;

import com.example.sitereceitas.classes.assoc_ingrediente.model.common.AssocIngrediente;
import com.example.sitereceitas.classes.usuario.model.common.Usuario;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="tb_receita")
public class Receita {

	// Relações
	@ManyToMany(mappedBy = "favoritos")
	private Set<Usuario> usuariosFavoritos = new HashSet<>();

	@OneToMany(mappedBy = "receita")
	private Set<AssocIngrediente> ingredientes;

	// Colunas
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idreceita")
	private Long idreceita;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;

	@NotNull
	@Column(name="verificada")
	private Boolean verificada = false;

	@NotEmpty
	@Column(name = "nome")
	private String nome;

	@Column(name="imagem")
	private byte[] imagem;

	@Column(name="descricao")
	private String descricao;

	@NotNull
	@Column(name="modo_preparo")
	private String modo_preparo;

	@NotNull
	@Column(name="tempo_preparo")
	private Integer tempo_preparo;

	@NotNull
	@Column(name="rendimento")
	private Integer rendimento;

	@Column(name="informacoes_adicionais")
	private String informacoes_adicionais;

	// Métodos
	@Override
	public int hashCode() {
		return Math.toIntExact(idreceita);
	}
}

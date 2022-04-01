package com.example.sitereceitas.classes.receita.model.dto;

import com.example.sitereceitas.classes.assoc_ingrediente.model.dto.AssocIngredienteDTO;
import lombok.Data;

@Data
public class ReceitaDTO {

	private Long idreceita;
	private String nome_usuario;
	private Boolean favorito = false;
	private Boolean logadoComoAutor = false;
	private Boolean verificada;
	private String nome;
	private String descricao;
	private Integer tempo_preparo;
	private Integer rendimento;
	private String modo_preparo;
	private AssocIngredienteDTO[] ingredientes;
	private String informacoes_adicionais;
	private String imagem;
}

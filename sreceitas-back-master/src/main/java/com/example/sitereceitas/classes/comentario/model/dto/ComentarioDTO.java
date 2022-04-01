package com.example.sitereceitas.classes.comentario.model.dto;

import com.example.sitereceitas.classes.receita.model.dto.ReceitaDTO;
import lombok.Data;

import java.util.Date;

@Data
public class ComentarioDTO {
    Long idcomentario;
    ReceitaDTO receita;
    Boolean logadoComoAutor = false;
    String nome_usuario;
    String conteudo;
    Date timestamp;
}

package com.example.sitereceitas.classes.receita.model.dto;

import com.example.sitereceitas.classes.shared.common.PaginacaoDTO;
import lombok.Data;

@Data
public class FiltroDTO {

    private PaginacaoDTO paginacao;
    private String[] ingredientes;
}

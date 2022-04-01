package com.example.sitereceitas.classes.shared.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PaginacaoDTO implements Serializable {

    private Integer quantidadePorPaginas;
    private Integer numeroPaginas;
}

package com.example.sitereceitas.classes.assoc_ingrediente.model.dto;

import com.example.sitereceitas.classes.medida.model.common.Medida;
import lombok.Data;

@Data
public class AssocIngredienteDTO {

    private Long quantidade;
    private Medida medida;
    private String ingrediente;
    private String plural;
}

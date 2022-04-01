package com.example.sitereceitas.classes.assoc_ingrediente.model.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class AssocIngredienteKey implements Serializable {

    public AssocIngredienteKey(Long idreceita, Long idingrediente) {
        this.idreceita = idreceita;
        this.idingrediente = idingrediente;
    }

    @Column(name = "idreceita")
    private Long idreceita;

    @Column(name = "idingrediente")
    private Long idingrediente;
}

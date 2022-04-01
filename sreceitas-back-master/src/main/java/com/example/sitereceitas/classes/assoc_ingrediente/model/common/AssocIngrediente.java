package com.example.sitereceitas.classes.assoc_ingrediente.model.common;

import com.example.sitereceitas.classes.ingrediente.model.common.Ingrediente;
import com.example.sitereceitas.classes.medida.model.common.Medida;
import com.example.sitereceitas.classes.receita.model.common.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "as_ingrediente_receita")
@AllArgsConstructor
@NoArgsConstructor
public class AssocIngrediente {

    @EmbeddedId
    private AssocIngredienteKey id;

    @ManyToOne
    @MapsId("idreceita")
    @JoinColumn(name = "idreceita")
    private Receita receita;

    @ManyToOne
    @MapsId("ingrediente")
    @JoinColumn(name = "idingrediente")
    private Ingrediente ingrediente;

    @Column(name = "plural")
    private String plural;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idmedida")
    private Medida medida;

    @NotNull
    @Column(name = "quantidade")
    private Long quantidade;

}

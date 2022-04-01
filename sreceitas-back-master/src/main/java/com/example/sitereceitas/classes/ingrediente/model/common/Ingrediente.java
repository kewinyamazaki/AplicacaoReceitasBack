package com.example.sitereceitas.classes.ingrediente.model.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tb_ingrediente")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idingrediente")
    private Long idingrediente;

    @NotEmpty
    @Column(name = "nome")
    private String nome;
}

package com.example.sitereceitas.classes.medida.model.common;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "tb_medida")
public class Medida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmedida")
    private Long idmedida;

    @NotNull
    @Length(max = 127)
    @Column(name = "medida")
    private String medida;

    @NotNull
    @Length(max = 255)
    @Column(name = "formato")
    private String formato;
}

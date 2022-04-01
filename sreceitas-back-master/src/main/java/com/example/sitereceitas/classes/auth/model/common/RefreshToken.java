package com.example.sitereceitas.classes.auth.model.common;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@Table(name="tb_refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "data_criacao")
    private Instant dataCriacao;

}

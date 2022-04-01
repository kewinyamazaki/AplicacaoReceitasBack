package com.example.sitereceitas.classes.token_validacao.model.common;

import com.example.sitereceitas.classes.usuario.model.common.Usuario;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name="tb_token_validacao")
public class TokenDeValidacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtoken")
    private Long idtoken;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @NotNull
    @NotEmpty
    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "validade")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/YYYY")
    private Date validade;
}

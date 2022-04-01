package com.example.sitereceitas.classes.comentario.model.common;

import com.example.sitereceitas.classes.receita.model.common.Receita;
import com.example.sitereceitas.classes.usuario.model.common.Usuario;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name="tb_comentario")
@Data
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcomentario")
    private Long idcomentario;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idreceita")
    private Receita receita;

    @NotEmpty
    @Column(name = "conteudo")
    String conteudo;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/YYYY")
    @Column(name = "timestamp")
    private Date timestamp;
}

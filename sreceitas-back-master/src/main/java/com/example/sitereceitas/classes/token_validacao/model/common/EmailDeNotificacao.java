package com.example.sitereceitas.classes.token_validacao.model.common;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDeNotificacao {
    private String assunto;
    private String destinatario;
    private String nomeUsuario;
    private String urlToken;
}

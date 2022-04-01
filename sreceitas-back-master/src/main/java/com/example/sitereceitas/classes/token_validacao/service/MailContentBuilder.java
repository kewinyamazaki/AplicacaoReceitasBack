package com.example.sitereceitas.classes.token_validacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

    @Autowired
    private TemplateEngine templateEngine;

    String build(String nomeUsuario, String urlToken) {
        Context context = new Context();
        context.setVariable("nomeUsuario", nomeUsuario);
        context.setVariable("urlToken", urlToken);

        return templateEngine.process("mailTemplate", context);
    }
}

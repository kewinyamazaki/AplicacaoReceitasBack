package com.example.sitereceitas.classes.token_validacao.service;

import com.example.sitereceitas.exceptions.NegocioException;
import com.example.sitereceitas.classes.token_validacao.model.common.EmailDeNotificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static com.example.sitereceitas.util.MensagensUtil.MSG_ERRO_ENVIAR_EMAIL;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(EmailDeNotificacao email){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("panem.receitas@gmail.com", "Panem Receitas");
            messageHelper.setTo(email.getDestinatario());
            messageHelper.setSubject(email.getAssunto());
            messageHelper.setText(mailContentBuilder.build(email.getNomeUsuario(), email.getUrlToken()), true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (NegocioException e) {
            throw new NegocioException(MSG_ERRO_ENVIAR_EMAIL);
        }
    }
}

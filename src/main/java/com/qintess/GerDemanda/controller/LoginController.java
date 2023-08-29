package com.qintess.GerDemanda.controller;


import com.qintess.GerDemanda.model.GenericResponse;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.UsuarioService;
import com.qintess.GerDemanda.service.dto.LoginDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.exception.UserNotFoundException;
import com.qintess.GerDemanda.service.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;


    @PostMapping("/validaUsuario")
    public ResponseEntity<LoginDTO> validaUsuario(@Valid @RequestBody LoginDTO dto) {
        LoginDTO usuarioDTO = usuarioService.checkUsuario(dto);
        return Objects.nonNull(usuarioDTO) ? ResponseEntity.ok().body(usuarioDTO) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping(value = "/redefinirsenha")
    public ResponseEntity<GenericResponse> resetSenha(@RequestBody UsuarioDTO dto) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("backofficedg@qintess.com", "Qin#1006");
            }
        });
        Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        System.out.println(usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe()));
        System.out.println(usuario);
        if (usuario == null) {
            throw new UserNotFoundException();
        }
        String email = usuario.getEmail();
        String token = UUID.randomUUID().toString();
        usuarioService.createPasswordResetTokenForUser(usuario, token);
        String url = "http://localhost:4200/redefinirSenha/" + token;
        String emailBody = "Olá,\n\nVocê solicitou a redefinição da senha da sua conta.\n\n"
                + "Por favor, clique no link abaixo para redefinir sua senha:\n" + url +"\n\n"
                + "Se você não solicitou esta redefinição, por favor ignore este e-mail.\n\n"
                + "Atenciosamente,\nEquipe de suporte.";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("backofficedg@qintess.com"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email));
            msg.setSubject("Redefinir Senha");
            msg.setText(emailBody);
            Transport.send(msg);

        } catch (MessagingException e) {
            throw new MailSendException("Erro ao enviar e-mail.", e);
        }
        GenericResponse response = new GenericResponse("O email foi enviado com sucesso!");
        return ResponseEntity.ok(response);
    }
}
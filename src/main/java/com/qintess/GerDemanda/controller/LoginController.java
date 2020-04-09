package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.UsuarioService;
import com.qintess.GerDemanda.service.dto.UsuarioResumidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/validaUsuario")
    public ResponseEntity<UsuarioResumidoDTO> validaUsuario(@RequestParam(value = "re") String re, @RequestParam(value = "senha") String senha) {
        UsuarioResumidoDTO usuarioDTO = usuarioService.checkUsuario(re, senha);
        return Objects.nonNull(usuarioDTO) ? ResponseEntity.ok().body(usuarioDTO) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

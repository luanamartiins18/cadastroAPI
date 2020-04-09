package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.UsuarioService;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.dto.UsuarioResumidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/validaUsuario")
    public ResponseEntity<UsuarioResumidoDTO> validaUsuario(@Valid @RequestBody UsuarioResumidoDTO dto) {
        UsuarioResumidoDTO usuarioDTO = usuarioService.checkUsuario(dto);
        return Objects.nonNull(usuarioDTO) ? ResponseEntity.ok().body(usuarioDTO) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

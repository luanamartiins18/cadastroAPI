package com.qintess.GerDemanda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.qintess.GerDemanda.service.UsuarioService;

@RestController
@CrossOrigin
public class LoginController {

	@Autowired
	UsuarioService usuarioService;

    @GetMapping("/validaUsuario")
    public ResponseEntity<String> validaUsuario(@RequestParam(value = "re") String re, @RequestParam(value = "senha") String senha) {
        return usuarioService.checkUsuario(re, senha) ?
                new ResponseEntity<String>(HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }
}

package com.qintess.GerDemanda.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.UsuarioService;

@RestController
@CrossOrigin
public class LoginController {
		
	@GetMapping("/validaUsuario")
	
	public ResponseEntity<String> validaUsuario(@RequestParam(value = "re") String re, @RequestParam(value = "senha") String senha){

		UsuarioService usuarioService = new UsuarioService();

		
		return usuarioService.checkUsuario(re, senha) ? 				
				new ResponseEntity<String>(HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
					
	
	}	
}

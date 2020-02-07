package com.qintess.GerDemanda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.UsuarioService;

@RestController
@CrossOrigin
public class UsuarioController {

	
	@GetMapping("/usuario")
	ResponseEntity<Usuario> getUsuario(@RequestParam(value = "re") String re){
		
		UsuarioService usuarioService = new UsuarioService();		
		Usuario usuario = usuarioService.getUsuarioByRe(re);				
		
		return (usuario == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuario);		
	}	

	@GetMapping("/usuario-sigla")
	ResponseEntity<List<Usuario>> getUsuarioBySigla(@RequestParam(value = "id") int idSigla){
		
		UsuarioService usuarioService = new UsuarioService();		
		List<Usuario> usuarios = usuarioService.getUsuarioBySigla(idSigla);				
		
		return (usuarios.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuarios);		
	}	
	
}

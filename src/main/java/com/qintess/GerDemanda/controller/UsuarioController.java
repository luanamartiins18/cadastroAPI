package com.qintess.GerDemanda.controller;
import java.util.HashMap;
import java.util.List;

import com.qintess.GerDemanda.model.Cargo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.UsuarioService;

@RestController
@CrossOrigin
public class UsuarioController {

	
	@GetMapping("/usuario/{re}")
	ResponseEntity<Usuario> getUsuario(@PathVariable String re){
		
		UsuarioService usuarioService = new UsuarioService();		
		Usuario usuario = usuarioService.getUsuarioByRe(re);				
		
		return (usuario == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuario);		
	}	

	@GetMapping("/sigla/{id}/usuarios")
	ResponseEntity<List<Usuario>> getUsuarioBySigla(@PathVariable int id){
		
		UsuarioService usuarioService = new UsuarioService();		
		List<Usuario> usuarios = usuarioService.getUsuarioBySigla(id);				
		
		return ResponseEntity.ok().body(usuarios);		
	}	
	
	@GetMapping("/usuario/{id}/perfil")
	public HashMap<String, Object> getPerfilUsuario(@PathVariable int id){
		UsuarioService us = new UsuarioService();
		return us.getPerfilUsuario(id);
	}

	@GetMapping("/usuario/{re}/cargo")
	ResponseEntity<Cargo> getCargoByRe(@PathVariable String re){

		UsuarioService usuarioService = new UsuarioService();
		Cargo cargo = usuarioService.getCargoByRe(re);

		return (cargo == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(cargo);
	}

}

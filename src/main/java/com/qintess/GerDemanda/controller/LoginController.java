package com.qintess.GerDemanda.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.UsuarioService;

@RestController
public class LoginController {
		
	@GetMapping("/usuario")
	public Usuario getUsuario(@RequestParam(value = "id") int id){

		UsuarioService usuarioService = new UsuarioService();
		Usuario usuario = usuarioService.getUsuarioById(id); 
				
		return usuario;				
	
	}	
}

package com.qintess.GerDemanda.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
		
		return (usuarios.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuarios);		
	}	
	
}

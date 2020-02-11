package com.qintess.GerDemanda.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.service.MensagemService;

@RestController
@CrossOrigin
public class MensagemController {

	@GetMapping("/mensagens")
	public ResponseEntity<List<Mensagem>> getMensagens(){
	
		MensagemService mensagemService = new MensagemService();
		List<Mensagem> listaMensagens = mensagemService.getMensagens();
		
		if(listaMensagens.size() == 0) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok().body(listaMensagens);
		}
		
	}
	
	
	
}

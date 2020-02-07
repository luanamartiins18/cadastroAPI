package com.qintess.GerDemanda.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qintess.GerDemanda.model.Situacao;
import com.qintess.GerDemanda.service.SituacaoService;

@RestController
@CrossOrigin
public class SituacaoController {

	
	@GetMapping("/situacao")
	ResponseEntity<List<Situacao>> getSituacao(){
		
		SituacaoService situacaoService = new SituacaoService();
		List<Situacao> listaSit = situacaoService.getSituacao();
		
		return (listaSit.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaSit);
	}
}

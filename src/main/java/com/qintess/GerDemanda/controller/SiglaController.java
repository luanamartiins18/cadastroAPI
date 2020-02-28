package com.qintess.GerDemanda.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.service.SiglaService;

@RestController
@CrossOrigin
public class SiglaController {

	//Lista todas as situações em ordem alfabética
	@GetMapping("/siglas")
	ResponseEntity<List<Sigla>> getSigla(){
		
		SiglaService siglaService = new SiglaService();
		List<Sigla> listaSig = siglaService.getSiglas();
		
		return (listaSig.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaSig);
	}
}

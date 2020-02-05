package com.qintess.GerDemanda.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.service.OrdemFornecimentoService;

@RestController
public class OrdemFornecimentoController {

	
	@GetMapping("/ordemfornecimento")
	public List<OrdemFornecimento> getOrdemDeFornecimento(){		
		
		return new OrdemFornecimentoService().getOrdemDeFornecimento();	
		
	}
	
	
}

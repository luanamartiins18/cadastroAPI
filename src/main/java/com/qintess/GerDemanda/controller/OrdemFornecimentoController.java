package com.qintess.GerDemanda.controller;

import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.service.OrdemFornecimentoService;


@RestController
@CrossOrigin
public class OrdemFornecimentoController {

	
	@GetMapping("/ordens-fornecimento")
	public List<OrdemFornecimento> getOrdemDeFornecimento(){				
		return new OrdemFornecimentoService().getOrdemDeFornecimento();			
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "seta-situacao")
	public ResponseEntity<String> setSituacao(@RequestBody String request){
		OrdemFornecimentoService ofService = new OrdemFornecimentoService();
		
		JSONObject json = new JSONObject(request);
		
		String colaborador = json.getJSONObject("colabSit").getString("colaborador");
		String situacao = json.getJSONObject("colabSit").getString("situacao");
		int id = json.getInt("id");
		
		System.out.println(colaborador);
		System.out.println(situacao);
		System.out.println(id);
		
		
		
		ofService.registraUsuSit(colaborador, situacao, id);
		
		return ResponseEntity.ok("Ok");		
	}
	
	
}

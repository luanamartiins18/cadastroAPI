package com.qintess.GerDemanda.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/ordem-fornecimento/usuario-situacao")
	public ResponseEntity<String> setSituacao(@RequestBody String request){
		
		OrdemFornecimentoService ofService = new OrdemFornecimentoService();		
		JSONObject json = new JSONObject(request);
		
		int situacao = json.getInt("sit");			
		int of = json.getInt("of");			
		
		ArrayList<Integer> listaUsu = new ArrayList<Integer>();
		JSONArray usuarios = json.getJSONArray("usu");
		
		for(int i=0; i<usuarios.length(); i++) {
			listaUsu.add(usuarios.getInt(i));
		}		
		
		ofService.registraUsuSit(listaUsu, situacao, of);		
		return ResponseEntity.ok("Ok");		
	}
	
	
}

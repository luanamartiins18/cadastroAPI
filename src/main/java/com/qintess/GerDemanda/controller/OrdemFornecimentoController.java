package com.qintess.GerDemanda.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.service.OrdemFornecimentoService;
	

@RestController
@CrossOrigin
public class OrdemFornecimentoController {

	
	@GetMapping("/ordem-fornecimento/{id}/usuarios")
	public ResponseEntity<List<Integer>> getUsuariosOf(@PathVariable int id){
		OrdemFornecimentoService ofService = new OrdemFornecimentoService();
		
		List<Integer> usuariosOf = ofService.getUsuariosOf(id);
		
		return ResponseEntity.ok().body(usuariosOf);
	}
	
	@GetMapping("/ordem-fornecimento/{id}/situacao")
	public ResponseEntity<HashMap<String, Object>> getSituacaoOf(@PathVariable int id){
		OrdemFornecimentoService ofService = new OrdemFornecimentoService();
		
		HashMap<String, Object> sitOf = ofService.getSituacaoOf(id);
		
	
		return ResponseEntity.ok().body(sitOf);
	}
	
	
	@GetMapping("/ordens-fornecimento")
	public List<OrdemFornecimento> getOrdemDeFornecimento(){				
		return new OrdemFornecimentoService().getOrdemDeFornecimento();			
	}
	
	@GetMapping("/ordem-fornecimento/{id}")
	public OrdemFornecimento getOrdemDeFornecimento(@PathVariable int id){				
		return new OrdemFornecimentoService().getOrdemDeFornecimento(id);			
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/ordem-fornecimento/usuario-situacao")
	public ResponseEntity<String> setSituacao(@RequestBody String request){
		
		OrdemFornecimentoService ofService = new OrdemFornecimentoService();		
		JSONObject json = new JSONObject(request);
		
		int situacao = json.getInt("sit");			
		int of = json.getInt("of");			
		String ref = json.getString("ref");
				
		ArrayList<Integer> listaUsu = new ArrayList<Integer>();
		JSONArray usuarios = json.getJSONArray("usu");
		
		for(int i=0; i<usuarios.length(); i++) {
			listaUsu.add(usuarios.getInt(i));
		}		
		
		ofService.registraUsuSit(listaUsu, situacao, of, ref);		
		return new ResponseEntity<String>(HttpStatus.OK);		
	}
	
	@GetMapping("ordens-fornecimento/usuario/{id}")
	public List<HashMap<String, Object>> getOrdensFornUsu(@PathVariable int id){
		
		OrdemFornecimentoService ofs = new OrdemFornecimentoService();
		return ofs.getOrdensFornUsuario(id);
		
	}
	
	
}





















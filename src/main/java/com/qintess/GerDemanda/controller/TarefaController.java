package com.qintess.GerDemanda.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.HTTP;
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

import com.qintess.GerDemanda.service.MensagemService;
import com.qintess.GerDemanda.service.TarefaService;

@RestController
@CrossOrigin
public class TarefaController {	
	
	@GetMapping("/unidades-medidas")
	public List<HashMap<String, Object>> getUniMedidas() {
		
		TarefaService ts = new TarefaService();
		return ts.getUniMedidas();		
	}
	
	@GetMapping("/disciplinas")
	public List<HashMap<String, Object>> getDisciplinasUsu() {
		
		TarefaService ts = new TarefaService();
		return ts.getDisciplinas();		
	}
	
	@GetMapping("/complexidades")
	public List<HashMap<String, Object>> getComplexidades() {
		
		TarefaService ts = new TarefaService();
		return ts.getComplexidades();		
	}
	
	@GetMapping("/itens-guia")
	public ResponseEntity<List<HashMap<String, Object>>> getItensGuia() {
		
		TarefaService ts = new TarefaService();
		List<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
		
		
		try {
			res = ts.getItensGuia();
			return ResponseEntity.ok().body(res);
		}catch(Exception excp) {
			return new ResponseEntity<List<HashMap<String, Object>>>(HttpStatus.NOT_FOUND);			
		}			
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/tarefa")	
	public ResponseEntity<String> insereTarefa(@RequestBody String param){
		
		TarefaService tf = new TarefaService();
		
		tf.insereTarefa(param);
		return new ResponseEntity<String>(HttpStatus.OK);				
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/tarefa/atualiza")	
	public ResponseEntity<String> atualizaTarefa(@RequestBody String param){
		
		TarefaService tf = new TarefaService();
		
		tf.atualizaTarefa(param);
		return new ResponseEntity<String>(HttpStatus.OK);				
	}
	
	@GetMapping("/usuario/{idUsu}/ordem-forn/{idOf}/tarefas")
	public List<HashMap<String, Object>> tarefasUsu(@PathVariable int idUsu, @PathVariable int idOf){
		TarefaService ts = new TarefaService();
		return ts.getTarefasUsuario(idUsu, idOf);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value =  "tarefa/{id}")
	public void deletaTarefa(@PathVariable int id) {
		TarefaService ts = new TarefaService();
		ts.deletaTarefa(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value =  "tarefa/situacao")
	public ResponseEntity<String> alteraSituacaoTarefa(@RequestBody String param) {
		
		TarefaService ts = new TarefaService();
		JSONObject json = new JSONObject(param);
		
		int idTrf = json.getInt("idTrf");
		int idSit = json.getInt("idSit");
		
		ts.alteraSituacaoTarefa(idTrf, idSit);		
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping("/usuario/{idUsu}/ordem-forn/{idOf}/valor-tarefa")
	public ResponseEntity<HashMap<String, Integer>> getValorTarefa(@PathVariable int idUsu, @PathVariable int idOf){
		TarefaService ts = new TarefaService();
		
		HashMap<String, Integer> resultado = ts.getValorTarefa(idUsu, idOf);
		if(resultado == null) {
			return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.OK).ok().body(resultado);
		}
	}
	
	@GetMapping("/ordem-forn/{id}/numero")
	public String getNumOf(@PathVariable int id) {
		TarefaService ts = new TarefaService();
		return ts.getNumOf(id);
	}
	
	
}

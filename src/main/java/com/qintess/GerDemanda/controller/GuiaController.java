package com.qintess.GerDemanda.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qintess.GerDemanda.service.GuiaService;

@RestController
@CrossOrigin(methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.PUT})
public class GuiaController {
	
	@RequestMapping(method = RequestMethod.PUT, value = "/tarefa-guia")
	public ResponseEntity<String> atualizaTarefa(@RequestBody String tarefa){
		
		GuiaService gs = new GuiaService();		
		Boolean requestOk = true;
				
		try {			
			gs.atualizaTarefaGuia(tarefa);		
		
		}catch(Exception excp) {

			requestOk = false;						
		
		}
		
		if(!requestOk) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/tarefa-guia")
	public ResponseEntity<String> insereTarefa(@RequestBody String requestParam){
		System.out.println(requestParam);
		GuiaService gs = new GuiaService();
		boolean ok = true;		
		
		try {
			
			gs.insereTarefaGuia(requestParam);
			
		}catch(Exception excp) {
			
			ok = false;
			System.out.println(excp.getMessage());
		}
		
		if(ok) {
			
			return new ResponseEntity<String>(HttpStatus.OK);
			
		}else {
			
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
		
	}	
	
}

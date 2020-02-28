package com.qintess.GerDemanda.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.catalina.connector.Response;
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
import com.qintess.GerDemanda.model.Mensagem;

import com.qintess.GerDemanda.service.MensagemService;

@RestController
@CrossOrigin
public class MensagemController {
	
	
	@GetMapping("mensagens/usuario/{id}")
	public List<HashMap<String, Object>> getMensagensColaborador(@PathVariable int id){
		
		MensagemService ms = new MensagemService();
		return ms.getMensagensColaborador(id);
	}
	
	@GetMapping("mensagem/{id}")
	public List<HashMap<String, Object>> detalhaMensagem(@PathVariable int id){		
		MensagemService ms = new MensagemService();
		return ms.detalhaMensagem(id);
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/mensagem-lida")
	
	public ResponseEntity<String> marcaLida(@RequestBody String param){
		
		MensagemService mensagemService = new MensagemService();
		
		JSONObject json = new JSONObject(param);
		int idMsgUsu = json.getInt("idMsgUsu");
		
		mensagemService.marcaLida(idMsgUsu);
		
		return new ResponseEntity<String>(HttpStatus.OK);		
	}

	@RequestMapping(method = RequestMethod.POST, value = "/mensagem-geral")
	public ResponseEntity<String> insereMensagemGeral(@RequestBody String param){
		
		JSONObject json = new JSONObject(param);
		
		String corpo = json.getString("corpo");
		String dtExp = json.getString("dtExp");
		int idResp = json.getInt("idResp");
		String titulo = json.getString("titulo");		
		
		MensagemService mensagemService = new MensagemService();
		mensagemService.insereMensagemGeral(corpo, idResp, dtExp, titulo);
		return new ResponseEntity<String>(HttpStatus.OK);			
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/mensagem-sigla")
	public ResponseEntity<String> insereMensagemSigla(@RequestBody String param){
		
		JSONObject json = new JSONObject(param);
		
		String corpo = json.getString("corpo");
		String dtExp = json.getString("dtExp");
		int idResp = json.getInt("idResp");
		int idSigla = json.getInt("idSigla");
		String titulo = json.getString("titulo");		
		
		MensagemService mensagemService = new MensagemService();
		mensagemService.insereMensagemSigla(corpo, idResp, dtExp, idSigla, titulo);
		return new ResponseEntity<String>(HttpStatus.OK);			
	}
	
	@GetMapping("/mensagens")
	public ResponseEntity<List<HashMap<String, Object>>> getMensagens(){
		
		MensagemService mensagemService = new MensagemService();
		return ResponseEntity.ok().body(mensagemService.listaMensagens());		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/mensagem-status")	
	public ResponseEntity<String> alteraStatusMsg(@RequestBody String param){
		
		MensagemService mensagemService = new MensagemService();
		
		JSONObject json = new JSONObject(param);
		int idMsg = json.getInt("idMsg");
		String acao = json.getString("acao");
		
		mensagemService.alteraStatusMsg(idMsg, acao);
		
		return new ResponseEntity<String>(HttpStatus.OK);		
	}
	
	
}


































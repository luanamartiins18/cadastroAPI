package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.MensagemService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class MensagemController {
    @Autowired
    MensagemService mensagemService;

    @GetMapping("historico-mensagens/usuario/{id}")
    public List<HashMap<String, Object>> getAllMensagensColaborador(@PathVariable int id) {
        return mensagemService.getAllMensagensColaborador(id);
    }

    @GetMapping("mensagens/usuario/{id}")
    public List<HashMap<String, Object>> getMensagensColaborador(@PathVariable int id) {
        return mensagemService.getMensagensColaborador(id);
    }

    @GetMapping("mensagem/{id}")
    public List<HashMap<String, Object>> detalhaMensagem(@PathVariable int id) {
        return mensagemService.detalhaMensagem(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mensagem-lida")
    public ResponseEntity<String> marcaLida(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        int idMsgUsu = json.getInt("idMsgUsu");

        mensagemService.marcaLida(idMsgUsu);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mensagem-geral")
    public ResponseEntity<String> insereMensagemGeral(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        String corpo = json.getString("corpo");
        String dtExp = json.getString("dtExp");
        int idResp = json.getInt("idResp");
        String titulo = json.getString("titulo");

        mensagemService.insereMensagemGeral(corpo, idResp, dtExp, titulo);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mensagem-sigla")
    public ResponseEntity<String> insereMensagemSigla(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        String corpo = json.getString("corpo");
        String dtExp = json.getString("dtExp");
        int idResp = json.getInt("idResp");
        int idSigla = json.getInt("idSigla");
        String titulo = json.getString("titulo");

        mensagemService.insereMensagemSigla(corpo, idResp, dtExp, idSigla, titulo);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/mensagens")
    public ResponseEntity<List<HashMap<String, Object>>> getMensagens() {
        return ResponseEntity.ok().body(mensagemService.listaMensagens());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mensagem-status")
    public ResponseEntity<String> alteraStatusMsg(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        int idMsg = json.getInt("idMsg");
        String acao = json.getString("acao");

        mensagemService.alteraStatusMsg(idMsg, acao);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}


































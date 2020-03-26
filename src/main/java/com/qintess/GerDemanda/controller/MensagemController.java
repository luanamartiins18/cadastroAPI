package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.MensagemService;
import com.qintess.GerDemanda.service.dto.UsuarioMensagemDTO;
import com.qintess.GerDemanda.service.dto.MensagemInDTO;
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
    public List<UsuarioMensagemDTO> getAllMensagensByUsuario(@PathVariable int id) {
        return mensagemService.getAllMensagensByUsuarios(id);
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
    public ResponseEntity<String> insereMensagemGeral(@RequestBody MensagemInDTO dto) {
        mensagemService.insereMensagem(dto, "GERAL");
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mensagem-sigla")
    public ResponseEntity<String> insereMensagemSigla(@RequestBody MensagemInDTO dto) {
        mensagemService.insereMensagem(dto, "SIGLA");
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/mensagens")
    ResponseEntity<List<UsuarioMensagemDTO>> getMensagem() {
        List<UsuarioMensagemDTO> listaMensagem = mensagemService.getMensagem();
        return (listaMensagem.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaMensagem);
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


































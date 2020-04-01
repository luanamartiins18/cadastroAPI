package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.MensagemService;
import com.qintess.GerDemanda.service.UsuarioMensagemService;
import com.qintess.GerDemanda.service.dto.MensagemDTO;
import com.qintess.GerDemanda.service.dto.MensagemInDTO;
import com.qintess.GerDemanda.service.dto.UsuarioMensagemDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MensagemController {

    @Autowired
    MensagemService mensagemService;

    @Autowired
    UsuarioMensagemService usuarioMensagemService;

    @GetMapping("historico-mensagens/usuario/{id}")
    public List<UsuarioMensagemDTO> getAllMensagensByUsuario(@PathVariable Integer id) {
        return usuarioMensagemService.getAllMensagensByUsuarios(id);
    }

    @GetMapping("mensagens/usuario/{id}")
    public List<UsuarioMensagemDTO> getMensagensColaborador(@PathVariable Integer id) {
        return usuarioMensagemService.getMensagensColaborador(id);
    }

    @GetMapping("mensagem/{id}")
    public List<UsuarioMensagemDTO> detalhaMensagem(@PathVariable Integer id) {
        return usuarioMensagemService.detalhaMensagem(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mensagem-lida")
    public ResponseEntity<String> marcaLida(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        Integer idMsgUsu = json.getInt("idMsgUsu");
        usuarioMensagemService.marcaLida(idMsgUsu);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mensagem-geral")
    public ResponseEntity<String> insereMensagemGeral(@RequestBody MensagemInDTO dto) {
        mensagemService.insereMensagemGeral(dto);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mensagem-sigla")
    public ResponseEntity<String> insereMensagemSigla(@RequestBody MensagemInDTO dto) {
        mensagemService.insereMensagemSigla(dto);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/mensagens")
    ResponseEntity<List<MensagemDTO>> getMensagem() {
        List<MensagemDTO> listaMensagem = mensagemService.getMensagem();
        return (listaMensagem.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaMensagem);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mensagem-status")
    public ResponseEntity<String> alteraStatusMsg(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        Integer idMsg = json.getInt("idMsg");
        String acao = json.getString("acao");
        mensagemService.alteraStatusMsg(idMsg, acao);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}


































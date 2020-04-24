package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.TarefaOfService;
import com.qintess.GerDemanda.service.dto.TarefaOfDTO;
import com.qintess.GerDemanda.service.dto.TarefaOfDetalhadoDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class TarefaOfController {
    @Autowired
    TarefaOfService tarefaOfService;

    @PostMapping(value = "/tarefa")
    public ResponseEntity<String> insereTarefa(@RequestBody TarefaOfDTO dto) {
        tarefaOfService.insereTarefa(dto);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tarefa/atualiza")
    public ResponseEntity<String> atualizaTarefa(@RequestBody TarefaOfDTO dto) {
        JSONObject json = new JSONObject(dto);
        Integer idTrfOf = json.getInt("idTrfOf");
        tarefaOfService.atualizaTarefa(idTrfOf, dto);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsu}/ordem-forn/{idOf}/tarefas")
    ResponseEntity<List<TarefaOfDetalhadoDTO>> getTarefasUsuario(@PathVariable Integer idUsu, @PathVariable Integer idOf) {
        return ResponseEntity.ok().body(tarefaOfService.getTarefasUsuario(idUsu, idOf));
    }

    @DeleteMapping("/tarefa/{id}")
    public ResponseEntity<?> deletaTarefa(@PathVariable Integer id) {
        tarefaOfService.deletaTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "tarefa/situacao")
    public ResponseEntity<String> alteraSituacaoTarefa(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        Integer idTrf = json.getInt("idTrf");
        Integer idSit = json.getInt("idSit");
        tarefaOfService.alteraSituacaoTarefa(idTrf, idSit);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsu}/ordem-forn/{idOf}/valor-tarefa")
    public ResponseEntity<HashMap<String, Integer>> getValorTarefa(@PathVariable Integer idUsu, @PathVariable Integer idOf) {
        HashMap<String, Integer> resultado = tarefaOfService.getValorTarefa(idUsu, idOf);
        if (resultado == null) {
            return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.OK).ok().body(resultado);
        }
    }
}
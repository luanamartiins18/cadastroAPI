package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.TarefaOfService;
import com.qintess.GerDemanda.service.dto.TarefaOfDTO;
import com.qintess.GerDemanda.service.dto.TarefaOfDetalhadoDTO;
import com.qintess.GerDemanda.service.dto.TarefaOfValorDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
        return ResponseEntity.ok().body(tarefaOfService.getTarefasUsuarioDTO(idUsu, idOf));
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
    ResponseEntity<TarefaOfValorDTO> getUsuarioId(@PathVariable Integer idUsu, @PathVariable Integer idOf) {
        TarefaOfValorDTO tarefaOfValorDTO = tarefaOfService.getValorTarefa(idUsu, idOf);
        return Objects.isNull(tarefaOfValorDTO) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(tarefaOfValorDTO);
    }
}
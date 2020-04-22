package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.model.ComplexGuia;
import com.qintess.GerDemanda.model.UniMedida;
import com.qintess.GerDemanda.service.TarefaService;
import com.qintess.GerDemanda.service.dto.DisciplinaDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class TarefaController {
    @Autowired
    TarefaService tarefaService;

    @GetMapping("/unidades-medidas")
    ResponseEntity<List<UniMedida>> getUniMedidas() {
        List<UniMedida> lista = tarefaService.getUniMedidas();
        return (lista.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(lista);
    }

    @GetMapping("/disciplinas")
    ResponseEntity<List<DisciplinaDTO>> getDisciplinas() {
        List<DisciplinaDTO> lista = tarefaService.getDisciplinas();
        return (lista.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(lista);
    }

    @GetMapping("/complexidades")
    ResponseEntity<List<ComplexGuia>> getComplexidades() {
        List<ComplexGuia> lista = tarefaService.getComplexidades();
        return (lista.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(lista);
    }

    @GetMapping("/ordem-forn/{id}/numero")
    public String getNumO(@PathVariable Integer id) {
        return tarefaService.getNumOf(id);
    }

    @GetMapping("/itens-guia")
    public ResponseEntity<List<HashMap<String, Object>>> getItensGuia() {
        List<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
        try {
            res = tarefaService.getItensGuia();
            return ResponseEntity.ok().body(res);
        } catch (Exception excp) {
            return new ResponseEntity<List<HashMap<String, Object>>>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tarefa")
    public ResponseEntity<String> insereTarefa(@RequestBody String param) {
        tarefaService.insereTarefa(param);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tarefa/atualiza")
    public ResponseEntity<String> atualizaTarefa(@RequestBody String param) {
        tarefaService.atualizaTarefa(param);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsu}/ordem-forn/{idOf}/tarefas")
    public List<HashMap<String, Object>> tarefasUsu(@PathVariable Integer idUsu, @PathVariable Integer idOf) {
        return tarefaService.getTarefasUsuario(idUsu, idOf);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "tarefa/{id}")
    public void deletaTarefa(@PathVariable Integer id) {
        tarefaService.deletaTarefa(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "tarefa/situacao")
    public ResponseEntity<String> alteraSituacaoTarefa(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        Integer idTrf = json.getInt("idTrf");
        Integer idSit = json.getInt("idSit");
        tarefaService.alteraSituacaoTarefa(idTrf, idSit);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsu}/ordem-forn/{idOf}/valor-tarefa")
    public ResponseEntity<HashMap<String, Integer>> getValorTarefa(@PathVariable Integer idUsu, @PathVariable Integer idOf) {
        HashMap<String, Integer> resultado = tarefaService.getValorTarefa(idUsu, idOf);
        if (resultado == null) {
            return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.OK).ok().body(resultado);
        }
    }

}

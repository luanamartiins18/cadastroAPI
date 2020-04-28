package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.TarefaGuiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.PUT})
public class TarefaGuiaController {

    @Autowired
    TarefaGuiaService tarefaGuiaService;

    @RequestMapping(method = RequestMethod.PUT, value = "/tarefa-guia")
    public ResponseEntity<String> atualizaTarefa(@RequestBody String tarefa) {
        TarefaGuiaService gs = new TarefaGuiaService();
        Boolean requestOk = true;
        try {
            gs.atualizaTarefaGuia(tarefa);
        } catch (Exception excp) {
            requestOk = false;
        }
        if (!requestOk) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<String>(HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tarefa-guia")
    public ResponseEntity<String> insereTarefa(@RequestBody String requestParam) {
        System.out.println(requestParam);
        TarefaGuiaService gs = new TarefaGuiaService();
        boolean ok = true;
        try {
            gs.insereTarefaGuia(requestParam);
        } catch (Exception excp) {
            ok = false;
            System.out.println(excp.getMessage());
        }
        if (ok) {
            return new ResponseEntity<String>(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/atividades")
    public ResponseEntity<List<HashMap<String, Object>>> getAtividades() {
        try {
            return ResponseEntity.ok().body(tarefaGuiaService.getAtividades());
        } catch (Exception excp) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/itens-guia")
    public ResponseEntity<List<HashMap<String, Object>>> getItensGuia() {
        try {
            return ResponseEntity.ok().body(tarefaGuiaService.getItensGuia());
        } catch (Exception excp) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/atividadeJaExiste/{atividade}")
    public ResponseEntity<Boolean> atividadeJaExiste(@PathVariable String atividade) {
        try {
            return ResponseEntity.ok().body(tarefaGuiaService.atividadeJaExiste(atividade));
        } catch (Exception excp) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/criaNumeroAtividade/{idDisciplina}")
    public ResponseEntity<Integer> criaNumeroAtividade(@PathVariable Integer idDisciplina) {
        try {
            return ResponseEntity.ok().body(tarefaGuiaService.criaNumeroAtividade(idDisciplina));
        } catch (Exception excp) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/criaNumeroTarefa/{idDisciplina}/{atividade}")
    public ResponseEntity<Integer> criaNumeroTarefa(@PathVariable Integer idDisciplina, @PathVariable String atividade) {
        try {
            return ResponseEntity.ok().body(tarefaGuiaService.criaNumeroTarefa(idDisciplina, atividade));
        } catch (Exception excp) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/criaNumeroAtividadess/{idDisciplina}")
    public ResponseEntity<Integer> criaNumeroAtividadess(@PathVariable Integer idDisciplina) {
        try {
            return ResponseEntity.ok().body(tarefaGuiaService.criaNumeroAtividade(idDisciplina));
        } catch (Exception excp) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
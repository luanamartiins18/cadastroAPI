package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.GuiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.PUT})
public class GuiaController {

    @Autowired
    GuiaService guiaService;

    @RequestMapping(method = RequestMethod.PUT, value = "/tarefa-guia")
    public ResponseEntity<String> atualizaTarefa(@RequestBody String tarefa) {
        Boolean requestOk = true;
        try {
            guiaService.atualizaTarefaGuia(tarefa);
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
        boolean ok = true;
        try {
            guiaService.insereTarefaGuia(requestParam);
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
        boolean ok = true;
        List<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
        try {
            res = guiaService.getAtividades();
        } catch (Exception excp) {
            ok = false;
            System.out.println(excp.getMessage());
        }
        if (ok) {
            return new ResponseEntity<List<HashMap<String, Object>>>(HttpStatus.OK).ok().body(res);
        } else {
            return new ResponseEntity<List<HashMap<String, Object>>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}




















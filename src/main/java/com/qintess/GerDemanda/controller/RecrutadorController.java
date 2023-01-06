package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.RecrutadorService;
import com.qintess.GerDemanda.service.dto.RecrutadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/recrutador")
public class RecrutadorController {

    @Autowired
    RecrutadorService recrutadorService;

    @GetMapping()
    ResponseEntity<List<RecrutadorDTO>> getRecrutador() {
        List<RecrutadorDTO> listaRecrutador = recrutadorService.getRecrutador();
        return (listaRecrutador.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaRecrutador);
    }
}

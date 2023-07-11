package com.qintess.GerDemanda.controller;


import com.qintess.GerDemanda.service.ContratoService;
import com.qintess.GerDemanda.service.dto.ContratoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/contrato")
public class ContratoController {

    @Autowired
    ContratoService contratoService;

    @GetMapping()
    ResponseEntity<List<ContratoDTO>> getContrato() {
        List<ContratoDTO> listaContrato = contratoService.getContrato();
        return (listaContrato.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaContrato);
    }



}

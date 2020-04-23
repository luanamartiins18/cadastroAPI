package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.service.SiglaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/siglas")
public class SiglaController {

    @Autowired
    SiglaService siglaService;

    @GetMapping()
    ResponseEntity<List<Sigla>> getSigla() {
        List<Sigla> listaSig = siglaService.getSiglas();
        return (listaSig.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaSig);
    }
}
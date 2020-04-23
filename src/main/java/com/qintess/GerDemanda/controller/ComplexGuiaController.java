package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.model.ComplexGuia;
import com.qintess.GerDemanda.service.ComplexGuiaService;
import com.qintess.GerDemanda.service.DisciplinaService;
import com.qintess.GerDemanda.service.dto.DisciplinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/complexidades")
public class ComplexGuiaController {

    @Autowired
    ComplexGuiaService complexGuiaService;

    @GetMapping()
    ResponseEntity<List<ComplexGuia>> getComplexidades() {
        List<ComplexGuia> lista = complexGuiaService.getComplexidades();
        return (lista.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(lista);
    }
}
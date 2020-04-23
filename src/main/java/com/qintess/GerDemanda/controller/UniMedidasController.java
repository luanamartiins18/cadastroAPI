package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.model.UniMedida;
import com.qintess.GerDemanda.service.UniMedidasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/unidades-medidas")
public class UniMedidasController {

    @Autowired
    UniMedidasService uniMedidasService;

    @GetMapping()
    ResponseEntity<List<UniMedida>> getUniMedidas() {
        List<UniMedida> lista = uniMedidasService.getUniMedidas();
        return (lista.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(lista);
    }
}
package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.TipoService;
import com.qintess.GerDemanda.service.dto.TipoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



import java.util.List;
@RestController
@CrossOrigin
@RequestMapping(value = "/tipo")
public class TipoController {
    @Autowired
    TipoService tipoService;

    @GetMapping()
    ResponseEntity<List<TipoDTO>> getTipo() {
        List<TipoDTO> listaTipo = tipoService.getTipo();
        return (listaTipo.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaTipo);
    }
}

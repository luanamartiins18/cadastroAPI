package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.MemoriaService;
import com.qintess.GerDemanda.service.dto.MemoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/memoria")
public class MemoriaController {

    @Autowired
    MemoriaService memoriaService;

    @GetMapping()
    ResponseEntity<List<MemoriaDTO>> getMemoria() {
        List<MemoriaDTO> listaMemoria = memoriaService.getMemoria();
        return (listaMemoria.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaMemoria);
    }
}
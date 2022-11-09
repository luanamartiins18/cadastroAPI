package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.ModeloService;
import com.qintess.GerDemanda.service.dto.ModeloDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/modelo")
public class ModeloController {

    @Autowired
    ModeloService modeloService;

    @GetMapping()
    ResponseEntity<List<ModeloDTO>> getModelo() {
        List<ModeloDTO> listaModelo = modeloService.getModelo();
        return (listaModelo.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaModelo);
    }
}

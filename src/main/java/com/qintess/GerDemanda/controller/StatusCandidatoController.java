package com.qintess.GerDemanda.controller;


import com.qintess.GerDemanda.service.StatusCandidatoService;
import com.qintess.GerDemanda.service.dto.StatusCandidatoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/status_candidato")
public class StatusCandidatoController {


    @Autowired
    StatusCandidatoService statusCandidatoService;

    @GetMapping()
    ResponseEntity<List<StatusCandidatoDTO>> getStatusCandidato() {
        List<StatusCandidatoDTO> listaStatus = statusCandidatoService.getStatusCandidato();
        return (listaStatus.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaStatus);
    }
}

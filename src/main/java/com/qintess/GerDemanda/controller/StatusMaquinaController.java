package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.StatusMaquinaService;
import com.qintess.GerDemanda.service.dto.StatusMaquinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/statusMaquinas")
public class StatusMaquinaController {

    @Autowired
    StatusMaquinaService statusMaquinaService;

    @GetMapping()
    ResponseEntity<List<StatusMaquinaDTO>> getStatusMaquina() {
        List<StatusMaquinaDTO> listaStatus = statusMaquinaService.getStatusMaquinas();
        return (listaStatus.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaStatus);
    }
}
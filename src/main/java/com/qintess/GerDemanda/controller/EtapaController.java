package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.EtapaService;
import com.qintess.GerDemanda.service.dto.EtapaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/etapa")
public class EtapaController {


    @Autowired
    EtapaService etapaService;

    @GetMapping()
    ResponseEntity<List<EtapaDTO>> getEtapa() {
        List<EtapaDTO> listaEtapa = etapaService.getEtapa();
        return (listaEtapa.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaEtapa);
    }
}

package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.DemandaService;
import com.qintess.GerDemanda.service.dto.DemandaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/demanda")
public class DemandaController {

    @Autowired
    DemandaService demandaService;

     @GetMapping()
     ResponseEntity<List<DemandaDTO>> getDemanda() {
         List<DemandaDTO> listaDemanda = demandaService.getDemanda();
      return (listaDemanda.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaDemanda);
     }
}
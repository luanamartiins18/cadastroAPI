package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.CentroCustoService;
import com.qintess.GerDemanda.service.dto.CentroCustoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/centro")
public class CentroCustoController {

      @Autowired
      CentroCustoService centroService;

   @GetMapping()
    ResponseEntity<List<CentroCustoDTO>> getCentroCusto() {
           List<CentroCustoDTO> listaCentro = centroService.getCentroCusto();
             return (listaCentro.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaCentro);
         }
  }
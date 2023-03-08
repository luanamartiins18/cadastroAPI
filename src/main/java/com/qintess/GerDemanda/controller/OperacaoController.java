package com.qintess.GerDemanda.controller;


import com.qintess.GerDemanda.service.OperacaoService;
import com.qintess.GerDemanda.service.dto.OperacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/operacao")
public class OperacaoController {

     @Autowired
     OperacaoService operacaoService;

      @GetMapping()
      ResponseEntity<List<OperacaoDTO>> getOperacao() {
           List<OperacaoDTO> listaOperacao = operacaoService.getOperacao();
         return (listaOperacao.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaOperacao);
      }
}

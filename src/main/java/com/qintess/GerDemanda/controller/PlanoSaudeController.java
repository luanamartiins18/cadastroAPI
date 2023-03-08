package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.PlanoSaudeService;
import com.qintess.GerDemanda.service.dto.PlanoSaudeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/plano")
public class PlanoSaudeController {

    @Autowired
    PlanoSaudeService planoService;

    @GetMapping()
    ResponseEntity<List<PlanoSaudeDTO>> getPlanoSaude() {
        List<PlanoSaudeDTO> listaPlano = planoService.getPlanoSaude();
        return (listaPlano.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaPlano);
    }
}

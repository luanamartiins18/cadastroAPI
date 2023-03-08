package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.PlanoSaudePretensaoService;
import com.qintess.GerDemanda.service.dto.PlanoSaudeDTO;
import com.qintess.GerDemanda.service.dto.PlanoSaudePretensaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/plano_pretensao")
public class PlanoSaudePretensaoController {

    @Autowired
    PlanoSaudePretensaoService planoPretensaoService;

    @GetMapping()
    ResponseEntity<List<PlanoSaudePretensaoDTO>> getPlanoSaudePretensao() {
        List<PlanoSaudePretensaoDTO> listaPlano = planoPretensaoService.getPlanoSaudePretensao();
        return (listaPlano.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaPlano);
    }
}

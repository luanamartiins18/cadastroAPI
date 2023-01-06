package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.EspecialidadeService;
import com.qintess.GerDemanda.service.dto.EspecialidadeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/especialidade")
public class EspecialidadeController {

    @Autowired
    EspecialidadeService especialidadeService;

    @GetMapping()
    ResponseEntity<List<EspecialidadeDTO>> getEspecialidade() {
        List<EspecialidadeDTO> listaEspacialidade = especialidadeService.getEspecialidade();
        return (listaEspacialidade.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaEspacialidade);
    }
}

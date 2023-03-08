package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.EquipamentoService;
import com.qintess.GerDemanda.service.dto.EquipamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/equipamento")
public class EquipamentoController {

    @Autowired
    EquipamentoService equipamentoService;

    @GetMapping()
    ResponseEntity<List<EquipamentoDTO>> getEquipamento() {
        List<EquipamentoDTO> listaEquipamento = equipamentoService.getEquipamento();
        return (listaEquipamento.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaEquipamento);
    }
}
package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.VersaoGuiaService;
import com.qintess.GerDemanda.service.dto.VersaoGuiaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/versao-guia")
public class VersaoGuiaController {

    @Autowired
    VersaoGuiaService versaoGuiaService;

    @GetMapping()
    ResponseEntity<VersaoGuiaDTO> getVersaoAtualGuia() {
        VersaoGuiaDTO versaoguia = versaoGuiaService.getVersaoAtualGuia();
        return (versaoguia == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(versaoguia);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> atualizaVersaoGuia(@RequestBody VersaoGuiaDTO descricao) {
        try {
            versaoGuiaService.atualizaVersaoGuia(descricao);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
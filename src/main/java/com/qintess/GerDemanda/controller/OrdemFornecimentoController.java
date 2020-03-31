package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.OrdemFornecimentoService;
import com.qintess.GerDemanda.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
public class OrdemFornecimentoController {
    @Autowired
    OrdemFornecimentoService ordemFornecimentoService;

    @GetMapping("/ordem-fornecimento/{id}/usuarios")
    public ResponseEntity<List<Integer>> getUsuariosOf(@PathVariable Integer id) {
        List<Integer> usuariosOf = ordemFornecimentoService.getUsuariosOf(id);
        return ResponseEntity.ok().body(usuariosOf);
    }

    @GetMapping("/ordem-fornecimento/{id}/situacao")
    public ResponseEntity<OrdemFornecimentoResumidaDTO> getSituacaoOf(@PathVariable Integer id) {
        OrdemFornecimentoResumidaDTO ordemFornecimentoResumidaDTO = ordemFornecimentoService.findOrdemFornecimentoByIdDTO(id);
        return ResponseEntity.ok().body(ordemFornecimentoResumidaDTO);
    }

    @GetMapping("/ordens-fornecimento")
    ResponseEntity<List<OrdemFornecimentoDetalhadoDTO>> getOrdemDeFornecimento() {
        List<OrdemFornecimentoDetalhadoDTO> ordensfornecimento = ordemFornecimentoService.getOrdemDeFornecimento();
        return (ordensfornecimento == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(ordensfornecimento);
    }

    @GetMapping("/ordem-fornecimento/{id}")
    ResponseEntity<OrdemFornecimentoDTO> getOrdemDeFornecimento(@PathVariable Integer id) {
        return ResponseEntity.ok().body(ordemFornecimentoService.getOrdemDeFornecimento(id));
    }

    @PostMapping(value = "/ordem-fornecimento/usuario-situacao")
    public ResponseEntity<OrdemFornecimentoDTO> setSituacao(@RequestBody OrdemFornecimentoInDTO dto) {
        ordemFornecimentoService.registraUsuSit(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("ordens-fornecimento/usuario/{id}")
    ResponseEntity<List<OrdemFornecimentoFiltradoDTO>> getOrdensFornUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok().body(ordemFornecimentoService.getOrdensFornUsuario(id));
    }

    @GetMapping("ordens-fornecimento-concluida")
    ResponseEntity<List<OrdensConcluidasDTO>> findOrdensConcluidas() {
        List<OrdensConcluidasDTO> ordensconcluidas = ordemFornecimentoService.findOrdensConcluidas();
        return (ordensconcluidas == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(ordensconcluidas);
    }
}




















package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.VagasService;
import com.qintess.GerDemanda.service.dto.VagasDTO;
import com.qintess.GerDemanda.service.repositories.CandidatosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class VagasController {

    @Autowired
    CandidatosRepository candidatoRepository;
    @Autowired
    VagasService rhService;

    @PostMapping(value = "/vagas")
    public ResponseEntity<String> insereVagas(@Valid @RequestBody VagasDTO dto) {
        rhService.insereVagas(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    ResponseEntity<List<VagasDTO>> getVagas() {
        List<VagasDTO> listaStatus = rhService.getVagas();
        return (listaStatus.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaStatus);
    }

    @PutMapping(value = "/vagas/{id}")
    public ResponseEntity<String> atualizaVagas (@PathVariable Integer id, @Valid @RequestBody VagasDTO dto) {
        rhService.updateVagas(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vagas")
    ResponseEntity<List<VagasDTO>> getListaVagas() {
        List<VagasDTO> listaVagas = rhService.getListaVagas();
        return (listaVagas.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaVagas);
    }

    @PutMapping(value = "/vagasStatus/{id}")
    public ResponseEntity<String> atualizaStatus (@PathVariable Integer id) {
        rhService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vagasetapa/{idEtapa}")
    ResponseEntity<List<VagasDTO>> getListaVagasPorEtapa(@PathVariable Integer idEtapa) {
        List<VagasDTO> listausuario = rhService.getListaVagasPorEtapa(idEtapa);
        return (listausuario.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listausuario);
    }

    @GetMapping("/vagasqualitor/{nr}")
    ResponseEntity<VagasDTO> getVagasQualitor(@PathVariable String nr) {
        VagasDTO vagas = rhService.getVagasByNrQualitor(nr);
        return (vagas == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(vagas);
    }

    @GetMapping("/vagas/{id}")
    ResponseEntity<VagasDTO> getVagasId(@PathVariable Integer id) {
        VagasDTO vagas = rhService.findByIdDTO(id);
        return Objects.isNull(vagas) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(vagas);
    }

}

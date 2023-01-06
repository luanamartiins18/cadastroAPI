package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.RhService;
import com.qintess.GerDemanda.service.dto.RhDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class RhController {

    @Autowired
    RhService rhService;

    @PostMapping(value = "/candidatos")
    public ResponseEntity<String> insereCandidato(@Valid @RequestBody RhDTO dto) {
        rhService.insereCandidatos(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/candidatos/{id}")
    public ResponseEntity<String> atualizaCandidato (@PathVariable Integer id, @Valid @RequestBody RhDTO dto) {
        rhService.updateCandidatos(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/candidatos/{id}")
    ResponseEntity<RhDTO> getCandidatosId(@PathVariable Integer id) {
        RhDTO rh = rhService.findByIdDTO(id);
        return Objects.isNull(rh) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(rh);
    }

    @GetMapping("/candidatos")
    ResponseEntity<List<RhDTO>> getListaRh() {
        List<RhDTO> listaRh = rhService.getListaRh();
        return (listaRh.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaRh);
    }


    @PutMapping(value = "/candidatosStatus/{id}")
    public ResponseEntity<String> atualizaStatus (@PathVariable Integer id) {
        rhService.updateStatus(id);
        return ResponseEntity.ok().build();
    }
}

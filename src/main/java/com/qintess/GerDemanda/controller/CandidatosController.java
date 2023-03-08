package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.CandidatosService;
import com.qintess.GerDemanda.service.VagasService;
import com.qintess.GerDemanda.service.dto.CandidatosDTO;
import com.qintess.GerDemanda.service.mapper.repositories.CandidatosRepository;
import com.qintess.GerDemanda.service.mapper.repositories.VagasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;


@RestController
@CrossOrigin
@Slf4j
public class CandidatosController {

    @Autowired
    CandidatosService candidatoService;

    @Autowired
    VagasService vagasService;

    @Autowired
    VagasRepository vagasRepository;

    @Autowired
    CandidatosRepository candidatosRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Salvar arquivo no diretório local
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path path = Paths.get("C:\\Users\\Qintess\\bdCadastro\\GerDemandaAPI\\src\\uploads\\" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping(value = "/candidatos")
    public ResponseEntity<String> insereCandidatos(@Valid @RequestBody CandidatosDTO dto) {
        candidatoService.insereCandidatos(dto);
        System.out.println(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/candidatosvaga/{id}")
    ResponseEntity<List<CandidatosDTO>> getListaCandidatosPorVagas(@PathVariable Integer id ){
        List<CandidatosDTO> listacandidato = candidatoService.getListaCandidatosPorVaga(id);
        return (listacandidato.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listacandidato);
    }

    @PutMapping(value = "/candidatos/{id}")
    public ResponseEntity<String> atualizaCandidatos (@PathVariable Integer id, @Valid @RequestBody CandidatosDTO dto) {
        candidatoService.updateCandidatos(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/candidatos/{id}")
    ResponseEntity<CandidatosDTO> getCandidatosId(@PathVariable Integer id) {
        CandidatosDTO candidatos = candidatoService.findByIdDTO(id);
        return Objects.isNull(candidatos) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(candidatos);
    }

    @GetMapping("/candidatos")
    ResponseEntity<List<CandidatosDTO>> getListaCandidatos() {
        List<CandidatosDTO> listaCandidatos = candidatoService.getListaCandidatos();
        return (listaCandidatos.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaCandidatos);
    }

    @GetMapping("/candidatosstatus/{idStatus}")
    ResponseEntity<List<CandidatosDTO>> getListaCandidatoPorStatus(@PathVariable Integer idStatus) {
        List<CandidatosDTO> listausuario = candidatoService.getListaCandidatoPorStatus(idStatus);
        return (listausuario.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listausuario);
    }

    @PutMapping(value = "/desvincular/{id}")
    public ResponseEntity<String> desvincularCandidato (@PathVariable Integer id) {
        candidatoService.desvincularCandidato(id);
        return ResponseEntity.ok().build();
    }

}

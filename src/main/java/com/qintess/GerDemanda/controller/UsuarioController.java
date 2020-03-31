package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.CargoService;
import com.qintess.GerDemanda.service.UsuarioService;
import com.qintess.GerDemanda.service.PerfilService;
import com.qintess.GerDemanda.service.dto.CargoDTO;
import com.qintess.GerDemanda.service.dto.PerfilDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PerfilService perfilService;

    @Autowired
    CargoService cargoService;

    @GetMapping("/usuario/{re}")
    ResponseEntity<UsuarioDTO> getUsuario(@PathVariable String re) {
        UsuarioDTO usuario = usuarioService.getUsuarioByRe(re);
        return (usuario == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuario);
    }

    @GetMapping("/sigla/{idSigla}/usuarios")
    ResponseEntity<List<UsuarioDTO>> getUsuariosBySigla(@PathVariable Integer idSigla) {
        List<UsuarioDTO> usuarios = usuarioService.getUsuariosBySiglaDTO(idSigla);
        return ResponseEntity.ok().body(usuarios);
    }

    @GetMapping("/usuario/{idUsuario}/perfil")
    public PerfilDTO getPerfilUsuario(@PathVariable Integer idUsuario) {
        return perfilService.getPerfilUsuario(idUsuario);
    }

    @GetMapping("/usuario/{re}/cargo")
    ResponseEntity<CargoDTO> getCargoUsuarioByRe(@PathVariable String re) {
        CargoDTO cargo = cargoService.getCargoUsuarioByRe(re);
        return (cargo == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(cargo);
    }

    @PostMapping( value = "/usuario")
    public ResponseEntity<String> insereUsuario(@Valid @RequestBody UsuarioDTO dto) {
        usuarioService.insereUsuario(dto);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
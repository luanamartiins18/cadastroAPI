package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.model.Cargo;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.CargoService;
import com.qintess.GerDemanda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CargoService cargoService;

    @GetMapping("/usuario/{re}")
    ResponseEntity<Usuario> getUsuario(@PathVariable String re) {
        Usuario usuario = usuarioService.getUsuarioByRe(re);
        return (usuario == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuario);
    }

    @GetMapping("/sigla/{idSigla}/usuarios")
    ResponseEntity<List<Usuario>> getUsuarioBySigla(@PathVariable Integer idSigla) {
        List<Usuario> usuarios = usuarioService.getUsuarioBySigla(idSigla);
        return ResponseEntity.ok().body(usuarios);
    }

    @GetMapping("/usuario/{idUsuario}/perfil")
    public HashMap<String, Object> getPerfilUsuario(@PathVariable Integer idUsuario) {
        return usuarioService.getPerfilUsuario(idUsuario);
    }

    @GetMapping("/usuario/{re}/cargo")
    ResponseEntity<Cargo> getCargoByRe(@PathVariable String re) {
        Cargo cargo = cargoService.getCargoByRe(re);
        return (cargo == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(cargo);
    }
}

package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.model.Perfil;
import com.qintess.GerDemanda.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/perfil")
public class PerfilController {

    @Autowired
    PerfilService perfilService;

    @GetMapping()
    ResponseEntity<List<Perfil>> getPerfil() {
        List<Perfil> listaPerfil = perfilService.getPerfil();
        return (listaPerfil.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaPerfil);
    }
}

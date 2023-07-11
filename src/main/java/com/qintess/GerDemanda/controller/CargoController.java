package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.CargoService;
import com.qintess.GerDemanda.service.dto.CargoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/cargo")
public class CargoController {


    @Autowired
    CargoService cargoService;

    @GetMapping()
    ResponseEntity<List<CargoDTO>> getCargo() {
        List<CargoDTO> listaCargo = cargoService.getCargo();
        return (listaCargo.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaCargo);
    }
}
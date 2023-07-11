package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.Buservice;
import com.qintess.GerDemanda.service.dto.BuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/bu")
public class BuController {

    @Autowired
    Buservice buService;

    @GetMapping()
    ResponseEntity<List<BuDTO>> getBu() {
        List<BuDTO> listaBu = buService.getBu();
        return (listaBu.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaBu);
    }
}

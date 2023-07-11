package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.StatusService;
import com.qintess.GerDemanda.service.dto.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/status")
public class StatusController {

    @Autowired
    StatusService statusService;

    @GetMapping()
    ResponseEntity<List<StatusDTO>> getStatus() {
        List<StatusDTO> listaStatus = statusService.getStatus();
        return (listaStatus.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaStatus);
    }
}

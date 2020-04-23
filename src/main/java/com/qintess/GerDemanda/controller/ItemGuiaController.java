package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.ItemGuiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/item-guia")
public class ItemGuiaController {

    @Autowired
    ItemGuiaService itemGuiaService;
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItemGuia(@PathVariable Integer id) {
        itemGuiaService.deletaItemTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
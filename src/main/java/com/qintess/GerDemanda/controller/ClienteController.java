package com.qintess.GerDemanda.controller;


import com.qintess.GerDemanda.service.ClienteService;
import com.qintess.GerDemanda.service.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/cliente")
public class ClienteController {

  @Autowired
  ClienteService clienteService;

 @GetMapping()
 ResponseEntity<List<ClienteDTO>> getCliente() {
        List<ClienteDTO> listaCliente = clienteService.getCliente();
         return (listaCliente.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaCliente);
      }
}

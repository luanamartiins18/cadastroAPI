package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.repositories.ClienteRepository;
import com.qintess.GerDemanda.service.dto.BuDTO;
import com.qintess.GerDemanda.service.dto.ClienteDTO;
import com.qintess.GerDemanda.service.dto.ContratoDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.ClienteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ClienteMapper clienteMapper;

     public List<ClienteDTO> getCliente() {
   return clienteMapper.toDto(clienteRepository.findByOrderByDescricaoAsc());
   }

 }
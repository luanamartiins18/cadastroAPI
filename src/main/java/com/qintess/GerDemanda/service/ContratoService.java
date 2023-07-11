package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.service.dto.ContratoDTO;
import com.qintess.GerDemanda.service.mapper.ContratoMapper;
import com.qintess.GerDemanda.service.repositories.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoService {

    @Autowired
    ContratoRepository contratoRepository;
    @Autowired
    ContratoMapper contratoMapper;

    public List<ContratoDTO> getContrato() {
        return contratoMapper.toDto(contratoRepository.findByOrderByOperacaoAsc());
    }
}


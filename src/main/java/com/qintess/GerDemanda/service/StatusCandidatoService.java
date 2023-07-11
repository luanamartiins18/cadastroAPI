package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.service.dto.StatusCandidatoDTO;
import com.qintess.GerDemanda.service.mapper.StatusCandidatoMapper;
import com.qintess.GerDemanda.service.repositories.StatusCandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusCandidatoService {

    @Autowired
    StatusCandidatoRepository statusCandidatoRepository;
    @Autowired
    StatusCandidatoMapper statusCandidatoMapper;

    public List<StatusCandidatoDTO> getStatusCandidato() {
        return statusCandidatoMapper.toDto(statusCandidatoRepository.findByOrderByDescricaoAsc());
    }
}

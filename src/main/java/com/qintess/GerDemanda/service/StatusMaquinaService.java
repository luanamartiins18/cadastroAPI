package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.service.dto.StatusCandidatoDTO;
import com.qintess.GerDemanda.service.dto.StatusMaquinaDTO;
import com.qintess.GerDemanda.service.mapper.StatusCandidatoMapper;
import com.qintess.GerDemanda.service.mapper.StatusMaquinasMapper;
import com.qintess.GerDemanda.service.repositories.StatusCandidatoRepository;
import com.qintess.GerDemanda.service.repositories.StatusMaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusMaquinaService {

    @Autowired
    StatusMaquinaRepository statusMaquinaRepository;
    @Autowired
    StatusMaquinasMapper statusMaquinasMapper;

    public List<StatusMaquinaDTO> getStatusMaquinas() {
        return statusMaquinasMapper.toDto(statusMaquinaRepository.findByOrderByDescricaoAsc());
    }
}

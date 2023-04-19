package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.service.dto.EspecialidadeDTO;
import com.qintess.GerDemanda.service.mapper.EspecialidadeMapper;
import com.qintess.GerDemanda.service.repositories.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadeService {

    @Autowired
    EspecialidadeRepository especialidadeRepository;
    @Autowired
    EspecialidadeMapper especialidadeMapper;

    public List<EspecialidadeDTO> getEspecialidade() {
        return especialidadeMapper.toDto(especialidadeRepository.findByOrderByDescricaoAsc());
    }
}

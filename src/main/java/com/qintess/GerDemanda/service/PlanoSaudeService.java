package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.service.dto.PlanoSaudeDTO;
import com.qintess.GerDemanda.service.mapper.PlanoSaudeMapper;
import com.qintess.GerDemanda.service.repositories.PlanoSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoSaudeService {


    @Autowired
    PlanoSaudeRepository planoRepository;
    @Autowired
    PlanoSaudeMapper planoMapper;


    public List<PlanoSaudeDTO> getPlanoSaude() {
        return planoMapper.toDto(planoRepository.findByOrderByDescricaoAsc());
    }

}

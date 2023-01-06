package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.service.dto.RecrutadorDTO;
import com.qintess.GerDemanda.service.mapper.RecrutadorMapper;
import com.qintess.GerDemanda.service.mapper.repositories.RecrutadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecrutadorService {
    @Autowired
    RecrutadorRepository recrutadorRepository;
    @Autowired
    RecrutadorMapper recrutadorMapper;

    public List<RecrutadorDTO> getRecrutador() {
        return recrutadorMapper.toDto(recrutadorRepository.findByOrderByDescricaoAsc());
    }
}

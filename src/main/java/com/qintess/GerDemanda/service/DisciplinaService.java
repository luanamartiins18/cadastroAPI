package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.repositories.DisciplinaRepository;
import com.qintess.GerDemanda.service.dto.DisciplinaDTO;
import com.qintess.GerDemanda.service.mapper.DisciplinaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaMapper disciplinaMapper;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    public List<DisciplinaDTO> getDisciplinas() {
        return disciplinaMapper.toDto(this.disciplinaRepository.findAll());
    }

}
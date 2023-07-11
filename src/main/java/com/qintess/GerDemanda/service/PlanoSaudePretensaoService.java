package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.service.dto.PlanoSaudePretensaoDTO;
import com.qintess.GerDemanda.service.mapper.PlanoSaudePretensaoMapper;
import com.qintess.GerDemanda.service.repositories.PlanoSaudePretensaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoSaudePretensaoService {

    @Autowired
    PlanoSaudePretensaoRepository planoPretensaoRepository;
    @Autowired
    PlanoSaudePretensaoMapper planoPretensaoMapper;


    public List<PlanoSaudePretensaoDTO> getPlanoSaudePretensao() {
        return planoPretensaoMapper.toDto(planoPretensaoRepository.findByOrderByDescricaoAsc());
    }
}

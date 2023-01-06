package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.service.dto.EtapaDTO;
import com.qintess.GerDemanda.service.mapper.EtapaMapper;
import com.qintess.GerDemanda.service.mapper.repositories.EtapaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtapaService {

    @Autowired
    EtapaRepository etapaRepository;
    @Autowired
    EtapaMapper etapaMapper;

    public List<EtapaDTO> getEtapa() {
        return etapaMapper.toDto(etapaRepository.findByOrderByDescricaoAsc());
    }
}

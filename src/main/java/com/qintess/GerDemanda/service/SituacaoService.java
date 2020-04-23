package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Situacao;
import com.qintess.GerDemanda.repositories.SituacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SituacaoService {

    @Autowired
    SituacaoRepository situacaoRepository;

    public List<Situacao> getSituacao() {
        return situacaoRepository.findByOrderByDescricaoAsc();
    }
}
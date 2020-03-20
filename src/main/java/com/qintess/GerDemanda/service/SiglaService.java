package com.qintess.GerDemanda.service;

import java.util.List;

import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.repositories.SiglaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiglaService {

    @Autowired
    SiglaRepository repository;

    public List<Sigla> getSiglas() {
        return repository.findByOrderByDescricaoAsc();
    }
}

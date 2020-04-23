package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.ComplexGuia;
import com.qintess.GerDemanda.repositories.ComplexGuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplexGuiaService {

    @Autowired
    ComplexGuiaRepository complexGuiaRepository;

    public List<ComplexGuia> getComplexidades() {
        return complexGuiaRepository.findAll();
    }
}
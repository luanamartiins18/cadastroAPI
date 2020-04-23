package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.UniMedida;
import com.qintess.GerDemanda.repositories.UniMedidasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniMedidasService {

    @Autowired
    UniMedidasRepository uniMedidasRepository;

    public List<UniMedida> getUniMedidas() {
        return uniMedidasRepository.findAll();
    }

}
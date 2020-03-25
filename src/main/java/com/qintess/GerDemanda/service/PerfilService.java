package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Perfil;
import com.qintess.GerDemanda.repositories.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService {

    @Autowired
    PerfilRepository repository;

    public List<Perfil> getPerfil() {
        return repository.findByOrderByDescricaoAsc();
    }
}

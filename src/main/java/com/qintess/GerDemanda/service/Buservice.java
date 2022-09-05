package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.repositories.BuRepository;
import com.qintess.GerDemanda.service.dto.BuDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.BuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Buservice {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    BuRepository buRepository;
    @Autowired
    BuMapper buMapper;

    public BuDTO getBuUsuarioByRe(String re) {
        UsuarioDTO usuarioDTO = this.usuarioService.getUsuarioByRe(re);
        return usuarioDTO.getBu();
    }

    public List<BuDTO> getBu() {
        return  buMapper.toDto(buRepository.findByOrderByDescricaoAsc());
    }
}

package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.repositories.TipoRepository;
import com.qintess.GerDemanda.service.dto.TipoDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.TipoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TipoService {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    TipoRepository tipoRepository;
    @Autowired
    TipoMapper tipoMapper;

    public TipoDTO getTipoUsuarioByRe(String re) {
        UsuarioDTO usuarioDTO = this.usuarioService.getUsuarioByRe(re);
        return usuarioDTO.getTipo();
    }

    public List<TipoDTO> getTipo() {
        return tipoMapper.toDto(tipoRepository.findByOrderByDescricaoAsc());
    }
}

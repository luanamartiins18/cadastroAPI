package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.CargoMapper;
import com.qintess.GerDemanda.service.mapper.repositories.CargoRepository;
import com.qintess.GerDemanda.service.mapper.repositories.PerfilRepository;
import com.qintess.GerDemanda.service.dto.PerfilDTO;
import com.qintess.GerDemanda.service.mapper.PerfilMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    PerfilRepository perfilRepository;
    @Autowired
    PerfilMapper perfilMapper;

    public PerfilDTO getPerfilUsuarioByRe(String re) {
        UsuarioDTO usuarioDTO = this.usuarioService.getUsuarioByRe(re);
        return usuarioDTO.getPerfil();
    }

    public List<PerfilDTO> getPerfil() {
        return perfilMapper.toDto(perfilRepository.findByOrderByDescricaoAsc());
    }
}
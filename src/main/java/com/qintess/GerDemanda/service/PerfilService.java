package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Perfil;
import com.qintess.GerDemanda.repositories.PerfilRepository;
import com.qintess.GerDemanda.repositories.UsuarioPerfilRepository;
import com.qintess.GerDemanda.service.dto.PerfilDTO;
import com.qintess.GerDemanda.service.dto.UsuarioPerfilDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qintess.GerDemanda.service.mapper.UsuarioPerfilMapper;

import java.util.List;

@Service
public class PerfilService {
    public static final int STATUS_ATIVO_CODIGO = 1;

    @Autowired
    PerfilRepository repository;

    @Autowired
    UsuarioPerfilMapper usuarioPerfilMapper;

    @Autowired
    UsuarioPerfilRepository usuarioPerfilRepository;

    public List<Perfil> getPerfil() {
        return repository.findByOrderByDescricaoAsc();
    }

    public PerfilDTO getPerfilUsuario(int idUsu) {
        UsuarioPerfilDTO dto = usuarioPerfilMapper.toDto(
                usuarioPerfilRepository.findByUsuarioPerfilIdAndStatus(idUsu, STATUS_ATIVO_CODIGO));
        return dto.getPerfil();
    }
}

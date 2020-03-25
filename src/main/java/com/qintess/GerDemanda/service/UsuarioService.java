package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.model.UsuarioPerfil;
import com.qintess.GerDemanda.repositories.UsuarioPerfilRepository;
import com.qintess.GerDemanda.repositories.UsuarioRepository;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UsuarioService {

    public static final int STATUS_ATIVO_CODIGO = 1;
    public static final String STATUS_ATIVO_DESCRICAO = "Ativo";
    public static final int CARGO_COLABORADOR = 3;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioPerfilRepository usuarioPerfilRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public HashMap<String, Object> getPerfilUsuario(int idUsu) {
        UsuarioPerfil usuarioPerfil = usuarioPerfilRepository.findByUsuarioPerfilAndStatus(idUsu, STATUS_ATIVO_CODIGO);
        HashMap<String, Object> perfil = new HashMap<String, Object>();
        perfil.put("descricao", usuarioPerfil.getPerfil());
        return perfil;
    }

    public List<UsuarioDTO> getUsuariosAtivos() {
        return usuarioMapper.toDto(usuarioRepository.findByStatusAndCargoIdOrderByNomeAsc(STATUS_ATIVO_DESCRICAO, CARGO_COLABORADOR));
    }

    public List<Usuario> getUsuarioBySigla(int id) {
        return (usuarioRepository.findByStatusAndCargoIdAndListaSiglasSiglaIdOrderByNomeAsc(STATUS_ATIVO_DESCRICAO, CARGO_COLABORADOR, id));
    }

    public List<UsuarioDTO> getUsuarioBySiglaDTO(int id) {
        return usuarioMapper.toDto(this.getUsuarioBySigla(id));
    }

    public UsuarioDTO getUsuarioByRe(String re) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByCodigoRe(re));
    }

    public boolean checkUsuario(String re, String senha) {
        return this.usuarioRepository.existsByCodigoReAndSenha(re, senha);
    }
}
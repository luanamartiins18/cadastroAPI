package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Cargo;
import com.qintess.GerDemanda.model.Contrato;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.repositories.UsuarioPerfilRepository;
import com.qintess.GerDemanda.repositories.UsuarioRepository;
import com.qintess.GerDemanda.service.dto.CargoDTO;
import com.qintess.GerDemanda.service.dto.PerfilDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.dto.UsuarioPerfilDTO;
import com.qintess.GerDemanda.service.mapper.UsuarioMapper;
import com.qintess.GerDemanda.service.mapper.UsuarioPerfilMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    private UsuarioPerfilMapper usuarioPerfilMapper;

    public UsuarioDTO getUsuarioByRe(String re) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByCodigoRe(re));
    }

    public List<Usuario> getUsuariosBySigla(int id) {
        return (usuarioRepository.findByStatusAndCargoIdAndListaSiglasSiglaIdOrderByNomeAsc(STATUS_ATIVO_DESCRICAO, CARGO_COLABORADOR, id));
    }

    public List<UsuarioDTO> getUsuariosBySiglaDTO(int id) {
        return usuarioMapper.toDto(this.getUsuariosBySigla(id));
    }

    public PerfilDTO getPerfilUsuario(int idUsu) {
        UsuarioPerfilDTO dto = usuarioPerfilMapper.toDto(
                usuarioPerfilRepository.findByUsuarioPerfilIdAndStatus(idUsu, STATUS_ATIVO_CODIGO));
        return dto.getPerfil();
    }

    public CargoDTO getCargoUsuarioByRe(String re) {
        UsuarioDTO usuarioDTO = this.getUsuarioByRe(re);
        return usuarioDTO.getCargo();
    }

    public boolean checkUsuario(String re, String senha) {
        return this.usuarioRepository.existsByCodigoReAndSenha(re, senha);
    }

    public List<Usuario> getUsuariosAtivos() {
        return usuarioRepository.findByStatusAndCargoIdOrderByNomeAsc(STATUS_ATIVO_DESCRICAO, CARGO_COLABORADOR);
    }

    public List<UsuarioDTO> getUsuariosAtivosDTO() {
        return usuarioMapper.toDto(this.getUsuariosAtivos());
    }

    @Transactional
    public void insereUsuario(UsuarioDTO dto) {
        Usuario obj = usuarioMapper.toEntity(dto);
        obj.setStatus("Ativo");
        obj.setContrato(Contrato.builder().id(1).build());
        obj.setCargo(Cargo.builder().id(3).build());
        usuarioRepository.save(obj);
    }
}


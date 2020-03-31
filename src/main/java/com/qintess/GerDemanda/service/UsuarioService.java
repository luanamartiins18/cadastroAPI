package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Cargo;
import com.qintess.GerDemanda.model.Contrato;
import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.repositories.UsuarioRepository;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.UsuarioMapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    public static final String STATUS_ATIVO_DESCRICAO = "Ativo";
    public static final int CARGO_COLABORADOR = 3;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public UsuarioDTO getUsuarioByRe(String re) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByCodigoRe(re));
    }

    public List<Usuario> getUsuariosBySigla(int id) {
        return (usuarioRepository.findByStatusAndCargoIdAndListaSiglasSiglaIdOrderByNomeAsc(STATUS_ATIVO_DESCRICAO, CARGO_COLABORADOR, id));
    }

    public List<UsuarioDTO> getUsuariosBySiglaDTO(int id) {
        return usuarioMapper.toDto(this.getUsuariosBySigla(id));
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

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", Usuario.class.getName()));
    }

    public UsuarioDTO findByIdDTO(Integer id) {
        return usuarioMapper.toDto(this.findById(id));
    }

    public List<UsuarioDTO> getListaUsuarios() {
        return usuarioMapper.toDto(usuarioRepository.findByOrderByIdAsc());
    }

    @Transactional
    public void insereUsuario(UsuarioDTO dto) {
        Usuario obj = usuarioMapper.toEntity(dto);
        obj.setStatus(STATUS_ATIVO_DESCRICAO);
        obj.setContrato(Contrato.builder().id(1).build());
        obj.setCargo(Cargo.builder().id(3).build());
        obj.setSenha("123");
        usuarioRepository.save(obj);
    }

    @Transactional
    public void updateUsuario(Integer id ,UsuarioDTO dto) {
        Usuario objNew = usuarioMapper.toEntity(dto);
        objNew.setId(id);
        findById(id);
        usuarioRepository.saveAndFlush(objNew);
    }

    public void deleteById(Integer id) {
        Usuario usuario = this.findById(id);
        usuarioRepository.delete(usuario);
    }
}


package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.*;
import com.qintess.GerDemanda.repositories.UsuarioPerfilRepository;
import com.qintess.GerDemanda.repositories.UsuarioRepository;
import com.qintess.GerDemanda.repositories.UsuarioSiglaRepository;
import com.qintess.GerDemanda.service.dto.PerfilDTO;
import com.qintess.GerDemanda.service.dto.SiglaDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.dto.UsuarioResumidoDTO;
import com.qintess.GerDemanda.service.mapper.UsuarioMapper;
import com.qintess.GerDemanda.service.mapper.UsuarioResumidoMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    public static final String STATUS_ATIVO_DESCRICAO = "Ativo";
    public static final int CARGO_COLABORADOR = 3;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioSiglaRepository usuarioSiglaRepository;

    @Autowired
    UsuarioPerfilRepository usuarioPerfilRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioResumidoMapper usuarioResumidoMapper;

    public UsuarioDTO getUsuarioByRe(String re) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByCodigoRe(re));
    }

    public UsuarioDTO getUsuarioByRE(String re, Integer id) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByCodigoReAndIdNot(re, id));
    }

    public UsuarioDTO getUsuarioByBB(String bb, Integer id) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByCodigoBBAndIdNot(bb, id));
    }

    public UsuarioDTO getUsuarioByEmail(String email, Integer id) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByEmailAndIdNot(email, id));
    }

    public UsuarioDTO getUsuarioByCpf(String cpf, Integer id) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByCpfAndIdNot(cpf, id));
    }

    public List<Usuario> getUsuariosBySigla(Integer id) {
        return (usuarioRepository.findByStatusAndCargoIdAndListaSiglasSiglaIdOrderByNomeAsc(STATUS_ATIVO_DESCRICAO, CARGO_COLABORADOR, id));
    }

    public List<UsuarioDTO> getUsuariosBySiglaDTO(Integer id) {
        return usuarioMapper.toDto(this.getUsuariosBySigla(id));
    }

    public UsuarioResumidoDTO checkUsuario(UsuarioResumidoDTO dto) {
        UsuarioResumidoDTO usuarioResumidoDTO = usuarioResumidoMapper.toDto(
                this.usuarioRepository.findFirstByCodigoReAndSenha(dto.getCodigoRe(), dto.getSenha()));
        validaStatus(usuarioResumidoDTO);
        return usuarioResumidoDTO;
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
        return usuarioToDTO(this.findById(id));
    }

    public List<UsuarioDTO> getListaUsuarios() {
        return usuarioRepository.findByOrderByNomeAsc().stream().map(obj -> usuarioToDTO(obj)).collect(Collectors.toList());
    }

    @Transactional
    public void insereUsuario(UsuarioDTO dto) {
        validacao(dto);
        Usuario obj = usuarioMapper.toEntity(dto);
        obj.setNome(obj.getNome().toUpperCase());
        obj.setContrato(Contrato.builder().id(1).build());
        obj.setStatus(STATUS_ATIVO_DESCRICAO);
        obj.setEmpresa("Qintess");
        obj.setSenha(DigestUtils.sha256Hex(dto.getCpf()));
        obj.setPrimeiroAcesso(true);
        setPerfilAndSigla(dto.getListaSiglas(), dto.getListaPerfil(), obj);
        usuarioRepository.save(obj);
    }

    private void validacao(UsuarioDTO dto) {
        Integer id = Objects.isNull(dto.getId()) ? 0 : dto.getId();
        if (Objects.nonNull(getUsuarioByCpf(dto.getCpf(), id))) {
            throw new ValidationException("O CPF já está em uso");
        }
        if (Objects.nonNull(getUsuarioByEmail(dto.getEmail(), id))) {
            throw new ValidationException("O e-mail já está em uso");
        }
        if (Objects.nonNull(getUsuarioByRE(dto.getCodigoRe(), id))) {
            throw new ValidationException("O códigoRe já está em uso");
        }
        if (Objects.nonNull(getUsuarioByBB(dto.getCodigoBB(), id))) {
            throw new ValidationException("O códigoBB já está em uso");
        }
    }

    private void validaStatus(UsuarioResumidoDTO dto) {
        if(Objects.isNull(dto)){
            throw new RuntimeException("Usuário ou senha inválida");
        }
        if (!dto.getStatus().equals(STATUS_ATIVO_DESCRICAO)) {
            throw new RuntimeException("Sua conta está desativada!");
        }
    }

    @Transactional
    public void updateUsuario(Integer id, UsuarioDTO dto) {
        dto.setId(id);
        validacao(dto);
        Usuario objOld = findById(id);
        usuarioMapperUpdate(dto, objOld);
        usuarioSiglaRepository.deleteByUsuarioSiglaId(id);
        usuarioPerfilRepository.deleteByUsuarioPerfilId(id);
        usuarioRepository.save(objOld);
    }

    private void usuarioMapperUpdate(UsuarioDTO dto, Usuario objOld) {
        Usuario objNew = usuarioMapper.toEntity(dto);
        objOld.setCargo(objNew.getCargo());
        objOld.setNome(objNew.getNome().toUpperCase());
        objOld.setNascimento(objNew.getNascimento());
        objOld.setCodigoBB(objNew.getCodigoBB());
        objOld.setCelular(objNew.getCelular());
        objOld.setCodigoRe(objNew.getCodigoRe());
        objOld.setCpf(objNew.getCpf());
        objOld.setEmail(objNew.getEmail());
        objOld.setEmpresa(objNew.getEmpresa());
        objOld.setDemanda(objNew.getDemanda());
        setPerfilAndSigla(dto.getListaSiglas(), dto.getListaPerfil(), objOld);
    }

    public void deleteById(Integer id) {
        Usuario usuario = this.findById(id);
        usuarioRepository.delete(usuario);
    }

    public void alteraStatus(Integer id, String acao) {
        Usuario usuario = this.findById(id);
        usuario.setStatus("Ativo");
        if (acao.equals("desativar")) {
            usuario.setStatus("Desligado");
        }
        this.usuarioRepository.saveAndFlush(usuario);
    }


    private void setPerfilAndSigla(List<SiglaDTO> listaSigla, List<PerfilDTO> listaPerfil, Usuario obj) {
        obj.setListaSiglas(
                listaSigla
                        .stream().map(sigla ->
                        UsuarioSigla.builder()
                                .usuarioSigla(obj)
                                .sigla(Sigla.builder().id(sigla.getId()).build())
                                .status(1)
                                .build()
                ).collect(Collectors.toList()));

        obj.setListaPerfil(
                listaPerfil
                        .stream().map(perfil ->
                        UsuarioPerfil.builder()
                                .usuarioPerfil(obj)
                                .perfil(Perfil.builder().id(perfil.getId()).build())
                                .status(1)
                                .build()).collect(Collectors.toList()));
    }

    private UsuarioDTO usuarioToDTO(Usuario obj) {
        UsuarioDTO dto = usuarioMapper.toDto(obj);
        dto.setListaSiglas(
                obj.getListaSiglas().stream().map(item ->
                        SiglaDTO.builder()
                                .id(item.getSigla().getId())
                                .descricao(item.getSigla().getDescricao())
                                .build())
                        .collect(Collectors.toList()));

        dto.setListaPerfil(
                obj.getListaPerfil().stream().map(item ->
                        PerfilDTO.builder()
                                .id(item.getPerfil().getId())
                                .descricao(item.getPerfil().getDescricao())
                                .build())
                        .collect(Collectors.toList()));
        return dto;
    }

    @Transactional
    public void alteraSenha(Integer id, UsuarioResumidoDTO dto) {
        Usuario obj = findById(id);
        obj.setSenha(dto.getSenha());
        obj.setPrimeiroAcesso(false);
        usuarioRepository.save(obj);
    }

}


package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.model.Situacao;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.repositories.OrdemFornecimentoRepository;
import com.qintess.GerDemanda.repositories.UsuarioOrdemFornecimentoRepository;
import com.qintess.GerDemanda.service.dto.*;
import com.qintess.GerDemanda.service.mapper.OrdemFornecimentoFiltradoMapper;
import com.qintess.GerDemanda.service.mapper.OrdemFornecimentoMapper;
import com.qintess.GerDemanda.service.mapper.OrdemFornecimentoResumidaMapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrdemFornecimentoService {

    public static final int STATUS_ATIVO = 1;
    public static final int SITUCAO_EM_EXECUCAO = 6;
    @Autowired
    OrdemFornecimentoRepository ordemFornecimentoRepository;
    @Autowired
    UsuarioOrdemFornecimentoRepository usuarioOrdemFornecimentoRepository;
    @Autowired
    OrdemFornecimentoResumidaMapper ordemFornecimentoResumidaMapper;
    @Autowired
    OrdemFornecimentoMapper ordemFornecimentoMapper;
    @Autowired
    OrdemFornecimentoFiltradoMapper ordemFornecimentoFiltradoMapper;

    public List<Integer> getUsuariosOf(Integer idOf) {
        return (List<Integer>) this.usuarioOrdemFornecimentoRepository
                .findByStatusAndOrdemFornecimentoId(STATUS_ATIVO, idOf)
                .stream().map(obj -> obj.getUsuario().getId()).collect(Collectors.toList());
    }

    public OrdemFornecimento findOrdemFornecimentoById(Integer id) {
        return this.ordemFornecimentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", OrdemFornecimento.class.getName()));
    }

    public OrdemFornecimentoResumidaDTO findOrdemFornecimentoByIdDTO(Integer id) {
        return ordemFornecimentoResumidaMapper.toDto(this.findOrdemFornecimentoById(id));
    }

    public List<OrdensConcluidasDTO> findOrdensConcluidas() {
        return this.ordemFornecimentoRepository.findOrdensConcluidas()
                .stream().map(obj -> new OrdensConcluidasDTO(obj)).collect(Collectors.toList());
    }

    public List<OrdemFornecimentoDetalhadoDTO> getOrdemDeFornecimento() {
        return this.ordemFornecimentoRepository.getOrdemDeFornecimento()
                .stream().map(obj -> new OrdemFornecimentoDetalhadoDTO(obj)).collect(Collectors.toList());
    }

    public OrdemFornecimentoDTO getOrdemDeFornecimento(Integer id) {
        return ordemFornecimentoMapper.toDto(ordemFornecimentoRepository.findFirstByIdAndSituacaoGentiId(id, SITUCAO_EM_EXECUCAO));
    }

    public void registraUsuSit(OrdemFornecimentoInDTO dto) {
        OrdemFornecimento of = this.findOrdemFornecimentoById(dto.getOf());
        of.setReferencia(dto.getRef());
        of.setSituacaoUsu(Situacao.builder().id(dto.getSit()).build());

        List<UsuarioOrdemFornecimento> uofs = new ArrayList<>();
        List<UsuarioOrdemFornecimento> usuariosDaOf = dto.getUsu().stream()
                .map(id -> {
                    UsuarioOrdemFornecimento uof = of.getListaUsuarios().stream()
                            .filter(obj -> obj.getId().equals(id)).findFirst().orElse(null);
                    if (Objects.nonNull(uof)) {
                        uof.setDtExclusao(null);
                        uof.setStatus(1);
                        return uof;
                    }
                    return UsuarioOrdemFornecimento.builder()
                            .usuario(Usuario.builder().id(id).build())
                            .dtCriacao(new Date())
                            .status(STATUS_ATIVO)
                            .ordemFornecimento(
                                    OrdemFornecimento.builder()
                                            .id(dto.getOf())
                                            .build()
                            ).build();
                }).collect(Collectors.toList());

        List<UsuarioOrdemFornecimento> usuarioExcluidos = of.getListaUsuarios().stream()
                .filter(obj -> dto.getUsu().stream()
                        .filter(id -> !id.equals(obj.getId())).findFirst().isPresent()
                ).map(obj -> {
                    obj.setDtExclusao(new Date());
                    obj.setStatus(0);
                    return obj;
                }).collect(Collectors.toList());
        uofs.addAll(usuarioExcluidos);
        uofs.addAll(usuariosDaOf);
        of.setListaUsuarios(uofs);
        this.ordemFornecimentoRepository.saveAndFlush(of);
    }

    public List<OrdemFornecimentoFiltradoDTO> getOrdensFornUsuario(Integer id) {
        return ordemFornecimentoFiltradoMapper.toDto(usuarioOrdemFornecimentoRepository
                .findByOrdemFornecimentoSituacaoUsuIdAndUsuarioIdAndDtExclusaoIsNullAndStatusOrderByOrdemFornecimentoSiglaDescricaoAsc(SITUCAO_EM_EXECUCAO, id, STATUS_ATIVO));
    }

    public String getNumOf(Integer id) {
        return ordemFornecimentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", OrdemFornecimento.class.getName())).getNumeroOFGenti();
    }

    public UsuarioOrdemFornecimento getIdUsuOf(Integer usu, Integer of) {
        return usuarioOrdemFornecimentoRepository.
                findFirstByStatusAndUsuarioIdAndOrdemFornecimentoId(STATUS_ATIVO,usu,of);
    }
}
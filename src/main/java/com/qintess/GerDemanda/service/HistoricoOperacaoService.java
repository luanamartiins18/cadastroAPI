package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.HistoricoOperacao;
import com.qintess.GerDemanda.service.mapper.repositories.HistoricoOperacaoRepository;
import com.qintess.GerDemanda.service.dto.HistoricoOperacaoDTO;
import com.qintess.GerDemanda.service.mapper.HistoricoOperacaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoOperacaoService {

    @Autowired
    private HistoricoOperacaoDTO historicoOperacao;

    @Autowired
    private HistoricoOperacao historico;

    @Autowired
    private HistoricoOperacaoMapper historicoOperacaoMapper;

    @Autowired
    private HistoricoOperacaoRepository historicoOperacaoRepository;

    @Transactional
    public void insereHistoricoOperacao(HistoricoOperacao obj) {
        System.out.println(obj.getData_inicio());
         HistoricoOperacao usuario = new HistoricoOperacao();
         usuario.setData_final(obj.getData_final());
         usuario.setData_inicio(obj.getData_inicio());
         usuario.setVigente(obj.getVigente());
         usuario.setOperacao(obj.getOperacao());
         usuario.setUsuario(obj.getUsuario());
         historicoOperacaoRepository.save(usuario);
    }

     public List<HistoricoOperacaoDTO> findByOperacaoOrderByDataInicioDesc(Integer id) {
        return historicoOperacaoRepository.findByByOperacaoOrderByDataInicioDesc(id).stream().map(obj -> historicoToDTO(obj)).collect(Collectors.toList());

    }
     public HistoricoOperacao findUltimoHistoricoByOperacao(Integer id) {
        return historicoOperacaoRepository.findUltimoHistoricoByOperacao(id);
    }

     @Transactional
     public void updateUltimoHistoricoOperacao(Date data_final, String vigente, Integer id) {
        historicoOperacaoRepository.updateUltimoHistoricoOperacao(data_final, vigente, id);
     }

     private HistoricoOperacaoDTO historicoToDTO(HistoricoOperacao obj) {
        HistoricoOperacaoDTO dto = historicoOperacaoMapper.toDto(obj);
        return dto;
     }
  }
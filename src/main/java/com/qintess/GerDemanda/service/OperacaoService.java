   package com.qintess.GerDemanda.service;

  import com.qintess.GerDemanda.repositories.OperacaoRepository;
import com.qintess.GerDemanda.service.dto.ClienteDTO;
 import com.qintess.GerDemanda.service.dto.ContratoDTO;
 import com.qintess.GerDemanda.service.dto.OperacaoDTO;
 import com.qintess.GerDemanda.service.mapper.OperacaoMapper;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import java.util.List;


 @Service
public class OperacaoService {

     @Autowired
     OperacaoRepository operacaoRepository;
     @Autowired
     OperacaoMapper operacaoMapper;


      public List<OperacaoDTO> getOperacao() {
            return  operacaoMapper.toDto(operacaoRepository.findByOrderByDescricaoAsc());
    }

  }
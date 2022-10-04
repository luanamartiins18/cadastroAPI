package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.repositories.DemandaRepository;
import com.qintess.GerDemanda.service.dto.DemandaDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.DemandaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

     @Service
   public class DemandaService {

      @Autowired
      UsuarioService usuarioService;
      @Autowired
      DemandaRepository demandaRepository;
      @Autowired
      DemandaMapper demadaMapper;

    public DemandaDTO getDemandaUsuarioByRe(String re) {
    UsuarioDTO usuarioDTO = this.usuarioService.getUsuarioByRe(re);
            return usuarioDTO.getDemanda();
    }

        public List<DemandaDTO> getDemanda() {
          return demadaMapper.toDto(demandaRepository.findByOrderByDescricaoAsc());
    }
     }
package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.service.repositories.CentroCustoRepository;
import com.qintess.GerDemanda.service.dto.CentroCustoDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.CentroCustoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroCustoService {

      @Autowired
      UsuarioService usuarioService;
      @Autowired
      CentroCustoRepository centroRepository;
      @Autowired
      CentroCustoMapper centroMapper;

    public CentroCustoDTO getCentroUsuarioByRe(String re) {
        UsuarioDTO usuarioDTO = this.usuarioService.getUsuarioByRe(re);
        return usuarioDTO.getCentro();
        }

   public List<CentroCustoDTO> getCentroCusto() {
           return centroMapper.toDto(centroRepository.findByOrderByDescricaoAsc());
    }

}
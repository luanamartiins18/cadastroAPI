package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.repositories.ModeloRepository;

import java.util.List;

import com.qintess.GerDemanda.service.dto.MemoriaDTO;
import com.qintess.GerDemanda.service.dto.ModeloDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.ModeloMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ModeloService {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ModeloRepository modeloRepository;
    @Autowired
    ModeloMapper modeloMapper;

    public List<ModeloDTO> getModelo() {
        return modeloMapper.toDto(modeloRepository.findByOrderByDescricaoAsc());
    }

    public ModeloDTO getModeloUsuarioByRe(String re) {
        UsuarioDTO usuarioDTO = this.usuarioService.getUsuarioByRe(re);
        return usuarioDTO.getModelo();
    }
}

package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.service.mapper.repositories.MemoriaRepository;
import com.qintess.GerDemanda.service.dto.MemoriaDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.MemoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoriaService {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    MemoriaRepository memoriaRepository;
    @Autowired
    MemoriaMapper memoriaMapper;

    public List<MemoriaDTO> getMemoria() {
        return memoriaMapper.toDto(memoriaRepository.findByOrderByDescricaoAsc());
    }

    public MemoriaDTO getMemoriaUsuarioByRe(String re) {
        UsuarioDTO usuarioDTO = this.usuarioService.getUsuarioByRe(re);
        return usuarioDTO.getMemoria();
    }
}

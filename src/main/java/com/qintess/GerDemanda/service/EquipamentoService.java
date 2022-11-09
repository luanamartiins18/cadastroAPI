package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.repositories.EquipamentoRepository;
import com.qintess.GerDemanda.service.dto.BuDTO;
import com.qintess.GerDemanda.service.dto.EquipamentoDTO;
import com.qintess.GerDemanda.service.dto.MemoriaDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.EquipamentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EquipamentoRepository equipamentoRepository;
    @Autowired
    EquipamentoMapper equipamentoMapper;

    public List<EquipamentoDTO> getEquipamento() {
        return equipamentoMapper.toDto(equipamentoRepository.findByOrderByDescricaoAsc());
    }

    public EquipamentoDTO getEquipamentoUsuarioByRe(String re) {
        UsuarioDTO usuarioDTO = this.usuarioService.getUsuarioByRe(re);
        return usuarioDTO.getEquipamento();
    }

}

package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.HistoricoPerfil;
import com.qintess.GerDemanda.model.HistoricoUsuario;
import com.qintess.GerDemanda.service.dto.HistoricoPerfilDTO;
import com.qintess.GerDemanda.service.dto.PerfilDTO;
import com.qintess.GerDemanda.service.mapper.HistoricoPerfilMapper;
import com.qintess.GerDemanda.service.mapper.PerfilMapper;
import com.qintess.GerDemanda.service.mapper.repositories.HistoricoPerfilRepository;
import com.qintess.GerDemanda.service.mapper.repositories.HistoricoRepository;
import com.qintess.GerDemanda.service.dto.HistoricoUsuarioDTO;
import com.qintess.GerDemanda.service.mapper.HistoricoUsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoPerfilService {

    @Autowired
    private HistoricoPerfilDTO historicoPerfil;

    @Autowired
    private HistoricoPerfil historico;

    @Autowired
    private HistoricoPerfilMapper historicoPerfilMapper;

    @Autowired
    private PerfilMapper perfilMapper;

    @Autowired
    private HistoricoPerfilRepository historicoPerfilRepository;

    @Transactional
    public void insereHistoricoPerfil(HistoricoPerfil obj) {
        HistoricoPerfil usuario = new HistoricoPerfil();
        usuario.setData_final(obj.getData_final());
        usuario.setData_inicio(obj.getData_inicio());
        usuario.setVigente(obj.getVigente());
        usuario.setPerfil(obj.getPerfil());
        usuario.setUsuario(obj.getUsuario());
        historicoPerfilRepository.save(usuario);
    }

    public List<HistoricoPerfilDTO> findByUsuarioOrderByDataInicioDesc(Integer id) {
        return historicoPerfilRepository.findByByUsuarioOrderByDataInicioDesc(id).stream().map(obj -> historicoPerfilToDTO(obj)).collect(Collectors.toList());

    }
    public HistoricoPerfil findUltimoHistoricoByUsuario(Integer id) {
        return historicoPerfilRepository.findUltimoHistoricoByUsuario(id);

    }

    @Transactional
    public void updateUltimoHistorico(Date data_final, String vigente, Integer id) {
        historicoPerfilRepository.updateUltimoHistoricoPerfil(data_final, vigente, id);
    }

    private HistoricoPerfilDTO historicoPerfilToDTO(HistoricoPerfil obj) {
        HistoricoPerfilDTO dto = historicoPerfilMapper.toDto(obj);
        PerfilDTO perfil = perfilMapper.toDto(obj.getPerfil());
        dto.setVigente(obj.getVigente());
        dto.setPerfil(perfil);
        return dto;
    }

    public void deleteById(Integer id) {
        historicoPerfilRepository.deleteByHistoricoId(id);
    }

}

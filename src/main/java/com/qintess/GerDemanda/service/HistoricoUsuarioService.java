package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.HistoricoUsuario;
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
public class HistoricoUsuarioService {

    @Autowired
    private HistoricoUsuarioDTO historicoUsuario;

    @Autowired
    private HistoricoUsuario historico;
    @Autowired
    private HistoricoUsuarioMapper historicoMapper;

    @Autowired
    private HistoricoRepository historicoRepository;

    @Transactional
    public void insereHistoricoUsuario(HistoricoUsuario obj) {
        HistoricoUsuario usuario = new HistoricoUsuario();
        usuario.setData_final(obj.getData_final());
        usuario.setData_inicio(obj.getData_inicio());
        usuario.setVigente(obj.getVigente());
        usuario.setCargo(obj.getCargo());
        usuario.setUsuario(obj.getUsuario());
        historicoRepository.save(usuario);
    }

    public List<HistoricoUsuarioDTO> findByUsuarioOrderByDataInicioDesc(Integer id) {
        return historicoRepository.findByByUsuarioOrderByDataInicioDesc(id).stream().map(obj -> historicoToDTO(obj)).collect(Collectors.toList());

    }
    public HistoricoUsuario findUltimoHistoricoByUsuario(Integer id) {
        return historicoRepository.findUltimoHistoricoByUsuario(id);

    }

    @Transactional
    public void updateUltimoHistorico(Date data_final, String vigente, Integer id) {
        historicoRepository.updateUltimoHistorico(data_final, vigente, id);
    }

    private HistoricoUsuarioDTO historicoToDTO(HistoricoUsuario obj) {
        HistoricoUsuarioDTO dto = historicoMapper.toDto(obj);
        dto.setVigente(obj.getVigente());
        return dto;
    }

    public void deleteById(Integer id) {
        historicoRepository.deleteByHistoricoId(id);
    }

}

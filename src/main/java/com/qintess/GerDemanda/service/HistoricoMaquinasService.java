package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.model.HistoricoMaquinas;
import com.qintess.GerDemanda.service.repositories.HistoricoMaquinasRepository;
import com.qintess.GerDemanda.service.dto.HistoricoMaquinasDTO;
import com.qintess.GerDemanda.service.mapper.HistoricoMaquinasMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoMaquinasService {

    @Autowired
    private HistoricoMaquinasDTO historicoMaquinas;

    @Autowired
    private HistoricoMaquinas historico;

    @Autowired
    private HistoricoMaquinasMapper historicoMaquinasMapper;

    @Autowired
    private HistoricoMaquinasRepository historicoMaquinasRepository;

    @Transactional
    public void insereHistoricoMaquinas(HistoricoMaquinas obj) {
        HistoricoMaquinas usuario = new HistoricoMaquinas();
        usuario.setData_final(obj.getData_final());
        usuario.setData_inicio(obj.getData_inicio());
        usuario.setTag(obj.getTag());
        usuario.setPatrimonio(obj.getPatrimonio());
        usuario.setVigente(obj.getVigente());
        usuario.setModelo(obj.getModelo());
        usuario.setMemoria(obj.getMemoria());
        usuario.setEquipamento(obj.getEquipamento());
        usuario.setUsuario(obj.getUsuario());
        historicoMaquinasRepository.save(usuario);
    }

    public List<HistoricoMaquinasDTO> findByMaquinasOrderByDataInicioDesc(Integer id) {
        return historicoMaquinasRepository.findByByMaquinasOrderByDataInicioDesc(id).stream().map(obj -> historicoToDTO(obj)).collect(Collectors.toList());

    }
    public HistoricoMaquinas findUltimoHistoricoByMaquinas(Integer id) {
        return historicoMaquinasRepository.findUltimoHistoricoByMaquinas(id);
    }

    @Transactional
    public void updateUltimoHistoricoMaquinas(Date data_inicio, Date data_final, String vigente, Integer id) {
        historicoMaquinasRepository.updateUltimoHistoricoMaquinas(data_inicio,data_final, vigente, id);
    }

    private HistoricoMaquinasDTO historicoToDTO(HistoricoMaquinas obj) {
        HistoricoMaquinasDTO dto = historicoMaquinasMapper.toDto(obj);
        return dto;
    }
}

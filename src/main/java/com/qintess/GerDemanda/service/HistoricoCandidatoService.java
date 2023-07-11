package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.HistoricoCandidato;
import com.qintess.GerDemanda.service.dto.HistoricoCandidatoDTO;
import com.qintess.GerDemanda.service.mapper.HistoricoCandidatoMapper;
import com.qintess.GerDemanda.service.repositories.CandidatosRepository;
import com.qintess.GerDemanda.service.repositories.HistoricoCandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoCandidatoService {

    @Autowired
    private HistoricoCandidatoRepository historicoCandidatoRepository;

    @Autowired
    private HistoricoCandidatoMapper historicoCandidatoMapper;

    @Autowired
    private CandidatosRepository candidatosRepository;

    @Transactional
    public void insereHistoricoCandidato(HistoricoCandidato obj) {
        HistoricoCandidato candidato = new HistoricoCandidato();
        candidato.setData_final(obj.getData_final());
        candidato.setData_inicio(obj.getData_inicio());
        candidato.setVigente(obj.getVigente());
        candidato.setStatus_candidato(obj.getStatus_candidato());
        candidato.setCandidatos(obj.getCandidatos());
        historicoCandidatoRepository.save(candidato);
    }


    public HistoricoCandidato findUltimoHistoricoByCandidato(Integer id) {
        return historicoCandidatoRepository.findUltimoHistoricoByCandidato(id);
    }
    public List<HistoricoCandidatoDTO> findByCandiatoOrderByDataInicioDesc(Integer id) {
        return historicoCandidatoRepository.findByCandiatoOrderByDataInicioDesc(id).stream().map(obj -> historicoToDTO(obj)).collect(Collectors.toList());

    }

    private HistoricoCandidatoDTO historicoToDTO(HistoricoCandidato obj) {
        HistoricoCandidatoDTO dto = historicoCandidatoMapper.toDto(obj);
        return dto;
    }

    @Transactional
    public void updateUltimoHistoricoCandidato(Date data_final, String vigente, Integer id) {
        historicoCandidatoRepository.updateUltimoHistoricoCandidato(data_final, vigente, id);
    }





}

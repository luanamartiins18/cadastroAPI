package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Candidatos;
import com.qintess.GerDemanda.model.HistoricoProposta;
import com.qintess.GerDemanda.service.dto.*;
import com.qintess.GerDemanda.service.mapper.CandidatosMapper;
import com.qintess.GerDemanda.service.mapper.HistoricoPropostaMapper;
import com.qintess.GerDemanda.service.mapper.ProspostaMapper;
import com.qintess.GerDemanda.service.mapper.StatusCandidatoMapper;
import com.qintess.GerDemanda.service.repositories.HistoricoPropostaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoPropostaService {

    @Autowired
    private HistoricoPropostaRepository historicoPropostaRepository;

    @Autowired
    private HistoricoPropostaMapper historicoPropostaMapper;

    @Autowired
    private CandidatosMapper candidatosMapper;

    @Autowired
    private ProspostaMapper propostaMapper;

    @Autowired
    private StatusCandidatoMapper statusMapper;

    @Transactional
    public void insereHistoricoProposta(HistoricoProposta obj) {
        HistoricoProposta proposta = new HistoricoProposta();
        proposta.setData_final(obj.getData_final());
        proposta.setData_inicio(obj.getData_inicio());
        proposta.setVigente(obj.getVigente());
        proposta.setProposta(obj.getProposta());
        proposta.setCandidatos(obj.getCandidatos());
        proposta.setStatus_candidatos(obj.getStatus_candidatos());
        historicoPropostaRepository.save(proposta);
    }

    public HistoricoProposta  findUltimoHistoricoByProposta(Integer id) {
        return  historicoPropostaRepository.findUltimoHistoricoByProposta(id);
    }

    @Transactional
    public void updateUltimoHistoricoProposta(Date data_final, String vigente, Integer id) {
        historicoPropostaRepository.updateUltimoHistoricoProposta(data_final, vigente, id);
    }

    private HistoricoPropostaDTO historicoToDTO(HistoricoProposta obj) {
        HistoricoPropostaDTO dto = historicoPropostaMapper.toDto(obj);
        CandidatosDTO candidatos = candidatosMapper.toDto(obj.getCandidatos());
        PropostaDTO proposta = propostaMapper.toDto(obj.getProposta());
        StatusCandidatoDTO status= statusMapper.toDto(obj.getStatus_candidatos());
        dto.setCandidatos(candidatos);
        dto.setProposta(proposta);
        dto.setStatus_candidatos(status);
        return dto;
    }

    public List<HistoricoPropostaDTO> findByPropostaOrderByDataInicioDesc(Integer id) {
        return historicoPropostaRepository.findByPropostaOrderByDataInicioDesc(id).stream().map(obj -> historicoToDTO(obj)).collect(Collectors.toList());

    }


    public List<HistoricoPropostaDTO> listaHistoricoPropostaPorCandidato(Candidatos candidatos){
        return historicoPropostaRepository.listaHistoricoPropostaPorCandidato(candidatos.getId()).stream().map(obj -> historicoToDTO(obj)).collect(Collectors.toList());
    }


    public HistoricoProposta findById(Integer id) {
        return historicoPropostaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", HistoricoProposta.class.getName()));
    }

    public HistoricoPropostaDTO findByIdDTO(Integer id) {
        return propostaToDTO(this.findById(id));
    }


    private HistoricoPropostaDTO propostaToDTO(HistoricoProposta obj) {
        HistoricoPropostaDTO dto = historicoPropostaMapper.toDto(obj);
        PropostaDTO status = propostaMapper.toDto(obj.getProposta());
        CandidatosDTO candidato = candidatosMapper.toDto(obj.getCandidatos());
        dto.setProposta(status);
        dto.setCandidatos(candidato);
        return dto;
    }

}

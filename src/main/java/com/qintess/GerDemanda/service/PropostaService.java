package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.model.Candidatos;
import com.qintess.GerDemanda.model.Proposta;
import com.qintess.GerDemanda.service.dto.CandidatosDTO;
import com.qintess.GerDemanda.service.dto.PropostaDTO;
import com.qintess.GerDemanda.service.dto.StatusCandidatoDTO;
import com.qintess.GerDemanda.service.mapper.CandidatosMapper;
import com.qintess.GerDemanda.service.mapper.ProspostaMapper;
import com.qintess.GerDemanda.service.mapper.StatusCandidatoMapper;
import com.qintess.GerDemanda.service.repositories.PropostaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PropostaService {

    @Autowired
    private ProspostaMapper propostaMapper;

    @Autowired
    StatusCandidatoMapper statusCandidatoMapper;


    @Autowired
    private CandidatosMapper candidatoMapper;

    @Autowired
    PropostaRepository propostaRepository;


    @Transactional
    public void
    insereProposta(PropostaDTO dto) {
        Proposta obj = propostaMapper.toEntity(dto);
        Candidatos candidato = candidatoMapper.toEntity(dto.getCandidatos());
        obj.setCandidatos(candidato);
        obj.setRemuneracao(dto.getRemuneracao());
        obj.setFlash(dto.getFlash());
        propostaRepository.save(obj);
    }

    public PropostaDTO newPropostaMapper(Proposta proposta) {
        PropostaDTO propostaDTO = propostaMapper.toDto(proposta);
        CandidatosDTO candidato = candidatoMapper.toDto(proposta.getCandidatos());
        propostaDTO.setCandidatos(candidato);
        return propostaDTO;
    }

    public PropostaDTO getPropostaById(Integer id) {
        return this.newPropostaMapper(propostaRepository.findPropostaById(id));
    }


    @Transactional
    public void updateProposta(Integer id, PropostaDTO dto) {
        dto.setId(id);
        Proposta objOld = findById(id);
        propostaMapperUpdate(dto, objOld);
        propostaRepository.save(objOld);
    }

    private void propostaMapperUpdate(PropostaDTO dto, Proposta objOld) {
        Proposta objNew = propostaMapper.toEntity(dto);
        Candidatos candidato = candidatoMapper.toEntity(dto.getCandidatos());
        objOld.setCandidatos(candidato);
        objOld.setStatus_candidatos(objNew.getStatus_candidatos());
        objOld.setRemuneracao(objNew.getRemuneracao());
        objOld.setFlash(objNew.getFlash());
    }


    public Proposta findById(Integer id) {
        return propostaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", Proposta.class.getName()));
    }

    public PropostaDTO findByIdDTO(Integer id) {
        return propostaToDTO(this.findById(id));
    }


    private PropostaDTO propostaToDTO(Proposta obj) {
        PropostaDTO dto = propostaMapper.toDto(obj);
        StatusCandidatoDTO status = statusCandidatoMapper.toDto(obj.getStatus_candidatos());
        dto.setStatus_candidatos(status);
        return dto;
    }



}

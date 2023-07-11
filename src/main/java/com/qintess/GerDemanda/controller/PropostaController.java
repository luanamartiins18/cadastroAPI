package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.model.Candidatos;
import com.qintess.GerDemanda.model.HistoricoProposta;
import com.qintess.GerDemanda.model.Proposta;
import com.qintess.GerDemanda.model.StatusCandidato;
import com.qintess.GerDemanda.service.CandidatosService;
import com.qintess.GerDemanda.service.HistoricoPropostaService;
import com.qintess.GerDemanda.service.PropostaService;
import com.qintess.GerDemanda.service.dto.HistoricoPropostaDTO;
import com.qintess.GerDemanda.service.dto.PropostaDTO;
import com.qintess.GerDemanda.service.mapper.CandidatosMapper;
import com.qintess.GerDemanda.service.mapper.ProspostaMapper;
import com.qintess.GerDemanda.service.mapper.StatusCandidatoMapper;
import com.qintess.GerDemanda.service.repositories.PropostaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Controller
@RestController
@CrossOrigin
@Slf4j
public class PropostaController {

    @Autowired
    PropostaService propostaService;

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    HistoricoProposta historicoProposta;

    @Autowired
    ProspostaMapper propostaMapper;

    @Autowired
    StatusCandidatoMapper statusCandidatoMapper;

    @Autowired
    HistoricoPropostaService historicoPropostaService;

    @Autowired
    CandidatosService candidatoService;

    @Autowired
    CandidatosMapper candidatoMapper;

    @PostMapping(value = "/proposta")
    public ResponseEntity<String> insereProposta(@Valid @RequestBody PropostaDTO dto) {
        propostaService.insereProposta(dto);
        // Verifique se o DTO contém o ID do candidato
        if (dto.getCandidatos() != null && dto.getCandidatos().getId() != null) {
            Proposta proposta = propostaRepository.findUltimaProposta();
            Candidatos candidatos = candidatoMapper.toEntity(dto.getCandidatos());
            StatusCandidato statusCandidatos = statusCandidatoMapper.toEntity(dto.getStatus_candidatos());
            Date dt = new Date();
            // Atualizar histórico anterior
            HistoricoProposta historico = historicoPropostaService.findUltimoHistoricoByProposta(proposta.getId());
            if (historico != null) {
                historicoPropostaService.updateUltimoHistoricoProposta(dt, "Não", historico.getId());
            }
            // Criar novo histórico
            HistoricoProposta novoHistorico = new HistoricoProposta();
            novoHistorico.setData_inicio(dt);
            novoHistorico.setCandidatos(candidatos);
            novoHistorico.setVigente("Sim");
            novoHistorico.setStatus_candidatos(statusCandidatos);
            novoHistorico.setProposta(proposta);
            historicoPropostaService.insereHistoricoProposta(novoHistorico);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/proposta/{id}")
    public ResponseEntity<String> atualizaProposta (@PathVariable Integer id, @Valid @RequestBody PropostaDTO dto) {
        Proposta proposta = propostaRepository.findFirstById(dto.getId());
        Candidatos candidatos = candidatoMapper.toEntity(dto.getCandidatos());
        StatusCandidato statusCandidatos = statusCandidatoMapper.toEntity(dto.getStatus_candidatos());
        propostaService.updateProposta(id, dto);
        Date dt = new Date();
        //Atualizar historico anterior
        HistoricoProposta historico = historicoPropostaService.findUltimoHistoricoByProposta(proposta.getId());
        if(historico != null) {
            historicoPropostaService.updateUltimoHistoricoProposta(dt, "Não", historico.getId());
        }
        //Insere novo historico
        historicoProposta.setData_inicio(dt);
        historicoProposta.setProposta(proposta);
        historicoProposta.setVigente("Sim");
        historicoProposta.setCandidatos(candidatos);
        historicoProposta.setStatus_candidatos(statusCandidatos);
        historicoPropostaService.insereHistoricoProposta(historicoProposta);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/historicoproposta/{id}")
    ResponseEntity<List<HistoricoPropostaDTO>> getListaHistoricoPropostaComId(@PathVariable Integer id) {
        Candidatos candidato = candidatoMapper.toEntity(candidatoService.findByIdDTO(id));
        List<HistoricoPropostaDTO> listahistoricoCandidato = historicoPropostaService.listaHistoricoPropostaPorCandidato(candidato);
        return (listahistoricoCandidato.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistoricoCandidato);
    }

    @GetMapping("/propostas/{id}")
    ResponseEntity<PropostaDTO> getPropostaId(@PathVariable Integer id) {
        PropostaDTO proposta = propostaService.findByIdDTO(id);
        return Objects.isNull(proposta) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(proposta);
    }

}

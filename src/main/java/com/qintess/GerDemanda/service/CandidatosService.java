package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Candidatos;
import com.qintess.GerDemanda.model.StatusCandidato;
import com.qintess.GerDemanda.service.dto.CandidatosDTO;
import com.qintess.GerDemanda.service.dto.StatusCandidatoDTO;
import com.qintess.GerDemanda.service.mapper.CandidatosMapper;
import com.qintess.GerDemanda.service.mapper.StatusCandidatoMapper;
import com.qintess.GerDemanda.service.repositories.CandidatosRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class CandidatosService {

    @Autowired
    CandidatosRepository candidatosRepository;

    @Autowired
    StatusCandidatoMapper statusCandidatoMapper;

    @Autowired
    private CandidatosMapper candidatosMapper;



    public CandidatosDTO findByIdDTO(Integer id) {
        return candidatosToDTO(this.findById(id));
    }

    public Candidatos findById(Integer id) {
        return candidatosRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", Candidatos.class.getName()));
    }

    public List<CandidatosDTO> getListaCandidatos() {
        return candidatosRepository.findByOrderByCandidatosAsc().stream().map(obj -> candidatosToDTO(obj)).collect(Collectors.toList());
    }

    public CandidatosDTO getCandidatoByCpf(String cpf, Integer id) {
        return candidatosMapper.toDto(candidatosRepository.findFirstByCpfAndIdNot(cpf, id));
    }

    public CandidatosDTO getCandidatoByEmail(String email, Integer id) {
        return candidatosMapper.toDto(candidatosRepository.findFirstByEmailAndIdNot(email, id));
    }

    public CandidatosDTO getCandidatoByTelefone(String telefone, Integer id) {
        return candidatosMapper.toDto(candidatosRepository.findFirstByTelefoneAndIdNot(telefone, id));
    }

    public List<CandidatosDTO> getListaCandidatosPorVaga(Integer id) {
        return candidatosRepository.listarCandidatosPorVaga(id).stream().map(obj -> candidatosToDTO(obj)).collect(Collectors.toList());
    }

    public List<CandidatosDTO> getListaCandidatoDisponivel() {
        return candidatosRepository.listarCandidatoDisponivel().stream().map(obj -> candidatosToDTO(obj)).collect(Collectors.toList());
    }

    public List<CandidatosDTO> getListaCandidatoPorStatus(Integer idStatus) {
        return candidatosRepository.listarCandidatoPorStatus(idStatus).stream().map(obj -> candidatosToDTO(obj)).collect(Collectors.toList());
    }


    public CandidatosDTO newCandidatoMapper(Candidatos candidatos) {
        CandidatosDTO candidatosDTO = candidatosMapper.toDto(candidatos);
        StatusCandidatoDTO statusCandiato = statusCandidatoMapper.toDto(candidatos.getStatus_candidato());
        candidatosDTO.setStatus_candidato(statusCandiato);
        return candidatosDTO;
    }

    public CandidatosDTO getCandidatoById(Integer id) {
        return this.newCandidatoMapper(candidatosRepository.findFirstById(id));
    }



    @Transactional
    public void insereCandidatos(CandidatosDTO dto) {
        validacao(dto);
        Candidatos obj = candidatosMapper.toEntity(dto);
        dto.setTelefone(obj.getTelefone());
        // Verificar se o candidato está vinculado a uma vaga
        if (obj.getVagas() != null) {
            obj.setStatus_candidato(StatusCandidato.builder().id(1).build()); // Vinculado à vaga (id = 1)
        } else {
            obj.setStatus_candidato(StatusCandidato.builder().id(6).build()); // Disponível (id = 6)
        }
        candidatosRepository.save(obj);
    }


    private void validacao(CandidatosDTO dto) {
        Integer id = Objects.isNull(dto.getId()) ? 0 : dto.getId();
        if (Objects.nonNull(getCandidatoByCpf(dto.getCpf(), id))) {
            throw new ValidationException("O CPF já está em uso");
        }
        if (Objects.nonNull(getCandidatoByEmail(dto.getEmail(), id))) {
            throw new ValidationException("O e-mail já está em uso");
        }
    }

    @Transactional
    public void updateCandidatos(Integer id, CandidatosDTO dto) {
        dto.setId(id);
        Candidatos objOld = findById(id);
        candidatosMapperUpdate(dto, objOld);
        candidatosRepository.save(objOld);
    }

    private void candidatosMapperUpdate(CandidatosDTO dto, Candidatos objOld) {
        Candidatos objNew = candidatosMapper.toEntity(dto);
        objOld.setCandidatos(objNew.getCandidatos().toUpperCase());
        objOld.setCpf(objNew.getCpf());
        objOld.setRg(objNew.getRg());
        objOld.setTelefone(objNew.getTelefone());
        objOld.setEmail(objNew.getEmail());
        objOld.setBonus_atual(objNew.getBonus_atual());
        objOld.setBonus_pretensao(objNew.getBonus_pretensao());
        objOld.setCesta_atual(objNew.getCesta_atual());
        objOld.setCesta_pretensao(objNew.getCesta_pretensao());
        objOld.setFlash_atual(objNew.getFlash_atual());
        objOld.setRemuneracao_atual(objNew.getRemuneracao_atual());
        objOld.setRemuneracao_pretensao(objNew.getRemuneracao_pretensao());
        objOld.setBonus_atual(objNew.getBonus_atual());
        objOld.setVale_alimentacao_pretensao(objNew.getVale_alimentacao_pretensao());
        objOld.setVale_alimentacao_atual(objNew.getVale_alimentacao_atual());
        objOld.setVale_refeicao_pretensao(objNew.getVale_refeicao_pretensao());
        objOld.setVale_refeicao_atual(objNew.getVale_refeicao_atual());
        objOld.setPlanoSaude(objNew.getPlanoSaude());
        objOld.setPlanoPretensao(objNew.getPlanoPretensao());
        objOld.setVagas(objNew.getVagas());
        objOld.setStatus_candidato(objNew.getStatus_candidato());
        objOld.setObservacao(objNew.getObservacao());
        objOld.setMotivo(null);
    }

    private CandidatosDTO candidatosToDTO(Candidatos obj) {
        CandidatosDTO dto = candidatosMapper.toDto(obj);
        StatusCandidatoDTO status = statusCandidatoMapper.toDto(obj.getStatus_candidato());
        dto.setStatus_candidato(status);
        dto.setTelefone(obj.getTelefone());
        return dto;
    }

    @Transactional
    public void desvincularCandidato(Integer id) {
        Candidatos objOld = findById(id);
        vagasMapperUpdateStatus(objOld );
        candidatosRepository.save(objOld);
    }
    private void vagasMapperUpdateStatus( Candidatos objOld ) {
        objOld.setVagas(null);
        objOld.setStatus_candidato(StatusCandidato.builder().id(6).build());
        objOld.setMotivo("O candidato estava vinculado a uma vaga, porém foi disponibilizado para uma nova vaga ");
    }
}

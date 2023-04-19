package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.model.*;
import com.qintess.GerDemanda.service.dto.*;
import com.qintess.GerDemanda.service.mapper.CandidatosMapper;
import com.qintess.GerDemanda.service.mapper.VagasMapper;
import com.qintess.GerDemanda.service.repositories.VagasRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VagasService {

    @Autowired
    VagasMapper vagasMapper;

    @Autowired
    VagasRepository vagasRepository;

    @Autowired
    private VagasMapper rhMapper;

    @Autowired
    private CandidatosMapper candidatosMapper;


    public List<VagasDTO> getListaVagas() {
        return vagasRepository.findByOrderByCargoAsc().stream().map(obj -> vagasToDTO(obj)).collect(Collectors.toList());
    }


    public Vagas findById(Integer id) {
        return vagasRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", Vagas.class.getName()));
    }

    public VagasDTO findByIdDTO(Integer id) {
        return vagasToDTO(this.findById(id));
    }


    public List<VagasDTO> getVagas() {
        return rhMapper.toDto(vagasRepository.findByOrderByIdAsc());
    }

    public List<VagasDTO> getListaVagasPorEtapa(Integer idEtapa) {
        return vagasRepository.listarVagasPorEtapa(idEtapa).stream().map(obj -> vagasToDTO(obj)).collect(Collectors.toList());
    }

    public VagasDTO getVagasByQualitor(String qualitor, Integer id) {
        return vagasMapper.toDto(vagasRepository.findFirstByQualitorAndIdNot(qualitor, id));
    }

    @Transactional
    public void
    insereVagas(VagasDTO dto) {
        validacao(dto);
        Date dt = new Date();
        Vagas obj = rhMapper.toEntity(dto);
        obj.setData_inicio(dt);
        obj.setStatus(Status.builder().id(1).build());
        obj.setEtapa(Etapa.builder().id(8).build());
        vagasRepository.save(obj);
    }


    private void validacao(VagasDTO dto) {
        Integer id = Objects.isNull(dto.getId()) ? 0 : dto.getId();
        if (Objects.nonNull(getVagasByQualitor(dto.getQualitor(), id))) {
            throw new ValidationException("O Nr Qaulitor já está em uso");
        }
    }

    @Transactional
    public void updateVagas(Integer id, VagasDTO dto) {
        dto.setId(id);
        Vagas objOld = findById(id);
        vagasMapperUpdate(dto, objOld);
        vagasRepository.save(objOld);
    }

    private void vagasMapperUpdate(VagasDTO dto, Vagas objOld) {
        Date dt = new Date();
        Vagas objNew = rhMapper.toEntity(dto);
        objOld.setQualitor(objNew.getQualitor());
        objOld.setVale_alimentacao(objNew.getVale_alimentacao());
        objOld.setVale_refeicao(objNew.getVale_refeicao());
        objOld.setRemuneracao(objNew.getRemuneracao());
        objOld.setBonus(objNew.getBonus());
        objOld.setCesta(objNew.getCesta());
        objOld.setFlash(objNew.getFlash());
        objOld.setStatus(Status.builder().id(2).build());
        objOld.setCargo(objNew.getCargo());
        objOld.setEspecialidade(objNew.getEspecialidade());
        objOld.setBu(objNew.getBu());
        objOld.setOperacao(objNew.getOperacao());
        objOld.setRecrutador(objNew.getRecrutador());
        objOld.setPlanoSaude(objNew.getPlanoSaude());
        objOld.setData_final(null);
        objOld.setEtapa(objNew.getEtapa());
    }

    private VagasDTO vagasToDTO(Vagas obj) {
        VagasDTO dto = rhMapper.toDto(obj);
        dto.setData_inicio(obj.getData_inicio());
        return dto;
    }

    @Transactional
    public void updateStatus(Integer id) {
        Vagas objOld = findById(id);
        vagasMapperUpdateStatus(objOld);
        vagasRepository.save(objOld);
    }
    private void vagasMapperUpdateStatus( Vagas objOld) {
        Date dt = new Date();
        objOld.setStatus(Status.builder().id(4).build());
        objOld.setData_final(dt);

    }
}

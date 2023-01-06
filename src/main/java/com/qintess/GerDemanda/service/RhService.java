package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.model.Etapa;
import com.qintess.GerDemanda.model.Rh;
import com.qintess.GerDemanda.model.Status;
import com.qintess.GerDemanda.service.dto.RhDTO;
import com.qintess.GerDemanda.service.mapper.RhMapper;
import com.qintess.GerDemanda.service.mapper.repositories.RhRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RhService {


    @Autowired
    RhRepository rhRepository;

    @Autowired
    private RhMapper rhMapper;


    public List<RhDTO> getListaRh() {
        return rhRepository.findByOrderByCandidatoAsc().stream().map(obj -> rhToDTO(obj)).collect(Collectors.toList());
    }



    public Rh findById(Integer id) {
        return rhRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", Rh.class.getName()));
    }


    public RhDTO findByIdDTO(Integer id) {
        return rhToDTO(this.findById(id));
    }


    @Transactional
    public void
    insereCandidatos(RhDTO dto) {
        Date dt = new Date();
        Rh obj = rhMapper.toEntity(dto);
        obj.setData_inicio(dt);
        obj.setStatus(Status.builder().id(1).build());
        obj.setEtapa(Etapa.builder().id(8).build());
        rhRepository.save(obj);
    }

    @Transactional
    public void updateCandidatos(Integer id, RhDTO dto) {
        dto.setId(id);
        Rh objOld = findById(id);
        rhMapperUpdate(dto, objOld);
        rhRepository.save(objOld);
    }

    private void rhMapperUpdate(RhDTO dto, Rh objOld) {
        Rh objNew = rhMapper.toEntity(dto);
        objOld.setCandidato(objNew.getCandidato().toUpperCase());
        objOld.setNumero_zoro(objNew.getNumero_zoro());
        objOld.setCpf(objNew.getCpf());
        objOld.setRg(objNew.getRg());
        objOld.setTelefone(objNew.getTelefone());
        objOld.setEmail(objNew.getEmail());
        objOld.setVale_alimentacao(objNew.getVale_alimentacao());
        objOld.setVale_refeicao(objNew.getVale_refeicao());
        objOld.setRemuneracao(objNew.getRemuneracao());
        objOld.setBonus(objNew.getBonus());
        objOld.setPlano_saude(objNew.getPlano_saude());
        objOld.setCesta(objNew.getCesta());
        objOld.setFlash(objNew.getFlash());
        objOld.setEtapa(objNew.getEtapa());
        objOld.setStatus(Status.builder().id(2).build());
        objOld.setCargo(objNew.getCargo());
        objOld.setEspecialidade(objNew.getEspecialidade());
        objOld.setBu(objNew.getBu());
        objOld.setOperacao(objNew.getOperacao());
        objOld.setRecrutador(objNew.getRecrutador());
    }

    private RhDTO rhToDTO(Rh obj) {
        RhDTO dto = rhMapper.toDto(obj);
        dto.setData_inicio(obj.getData_inicio());
        return dto;
    }

    @Transactional
    public void updateStatus(Integer id) {
        Rh objOld = findById(id);
        rhMapperUpdateStatus(objOld);
        rhRepository.save(objOld);
    }
    private void rhMapperUpdateStatus( Rh objOld) {
        objOld.setStatus(Status.builder().id(4).build());
    }

}

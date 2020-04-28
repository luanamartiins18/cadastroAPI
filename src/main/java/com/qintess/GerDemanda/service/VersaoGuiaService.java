package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.VersaoGuia;
import com.qintess.GerDemanda.repositories.VersaoGuiaRepository;
import com.qintess.GerDemanda.service.dto.VersaoGuiaDTO;
import com.qintess.GerDemanda.service.mapper.VersaoGuiaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersaoGuiaService {

    @Autowired
    VersaoGuiaRepository versaoGuiaRepository;

    @Autowired
    VersaoGuiaMapper versaoGuiaMapper;

    public VersaoGuiaDTO getVersaoAtualGuia() {
        List<VersaoGuia> versaoGuia = versaoGuiaRepository.findAll();
        if (versaoGuia.isEmpty()) {
            return null;
        }
        return versaoGuiaMapper.toDto(versaoGuia.get(0));
    }

    public boolean atualizaVersaoGuia(VersaoGuiaDTO versaoGuiaDTO) {
        List<VersaoGuia> lista = versaoGuiaRepository.findAll();
        versaoGuiaRepository.deleteAll(lista);
        VersaoGuia versaoGuia = new VersaoGuia();
        versaoGuia.setDescricao(versaoGuiaDTO.getVersao());
        versaoGuiaRepository.save(versaoGuia);
        return true;
    }
}
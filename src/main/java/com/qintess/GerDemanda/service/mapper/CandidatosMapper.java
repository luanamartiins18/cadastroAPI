package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Candidatos;
import com.qintess.GerDemanda.service.dto.CandidatosDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface CandidatosMapper extends EntityMapper<CandidatosDTO, Candidatos> {
}

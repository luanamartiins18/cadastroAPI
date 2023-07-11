package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.StatusCandidato;
import com.qintess.GerDemanda.service.dto.StatusCandidatoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface StatusCandidatoMapper extends EntityMapper<StatusCandidatoDTO, StatusCandidato>{
}

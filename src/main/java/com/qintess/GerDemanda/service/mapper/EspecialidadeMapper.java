package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Especialidade;
import com.qintess.GerDemanda.service.dto.EspecialidadeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EspecialidadeMapper extends EntityMapper<EspecialidadeDTO, Especialidade>{
}

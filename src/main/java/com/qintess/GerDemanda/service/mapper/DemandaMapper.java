package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Demanda;
import com.qintess.GerDemanda.service.dto.DemandaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface DemandaMapper extends EntityMapper<DemandaDTO, Demanda> {
 }
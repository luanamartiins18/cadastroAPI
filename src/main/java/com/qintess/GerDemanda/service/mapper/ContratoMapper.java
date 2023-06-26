package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Contrato;
import com.qintess.GerDemanda.service.dto.ContratoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ContratoMapper  extends EntityMapper<ContratoDTO, Contrato> {
}


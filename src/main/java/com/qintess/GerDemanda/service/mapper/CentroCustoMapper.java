package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.CentroCusto;
import com.qintess.GerDemanda.service.dto.CentroCustoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CentroCustoMapper extends EntityMapper<CentroCustoDTO, CentroCusto> {
}
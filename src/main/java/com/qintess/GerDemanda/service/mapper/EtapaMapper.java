package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Etapa;
import com.qintess.GerDemanda.service.dto.EtapaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EtapaMapper extends EntityMapper<EtapaDTO, Etapa> {
}

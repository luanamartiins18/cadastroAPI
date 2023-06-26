package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.HistoricoCandidato;
import com.qintess.GerDemanda.service.dto.HistoricoCandidatoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HistoricoCandidatoMapper extends EntityMapper<HistoricoCandidatoDTO, HistoricoCandidato> {
}

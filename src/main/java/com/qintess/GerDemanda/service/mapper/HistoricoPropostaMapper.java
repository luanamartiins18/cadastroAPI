package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.HistoricoProposta;
import com.qintess.GerDemanda.service.dto.HistoricoPropostaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HistoricoPropostaMapper extends EntityMapper<HistoricoPropostaDTO, HistoricoProposta>{
}

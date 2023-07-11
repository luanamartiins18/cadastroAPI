package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.HistoricoOperacao;
import com.qintess.GerDemanda.service.dto.HistoricoOperacaoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HistoricoOperacaoMapper extends EntityMapper<HistoricoOperacaoDTO, HistoricoOperacao>{
}

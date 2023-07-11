package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.HistoricoPerfil;
import com.qintess.GerDemanda.service.dto.HistoricoPerfilDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HistoricoPerfilMapper extends EntityMapper<HistoricoPerfilDTO, HistoricoPerfil>{
}


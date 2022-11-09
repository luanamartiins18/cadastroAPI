package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.HistoricoMaquinas;
import com.qintess.GerDemanda.service.dto.HistoricoMaquinasDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HistoricoMaquinasMapper extends EntityMapper<HistoricoMaquinasDTO, HistoricoMaquinas> {
}

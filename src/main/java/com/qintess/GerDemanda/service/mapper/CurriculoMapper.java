package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Curriculo;
import com.qintess.GerDemanda.service.dto.CurriculoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CurriculoMapper extends EntityMapper<CurriculoDTO, Curriculo> {
}


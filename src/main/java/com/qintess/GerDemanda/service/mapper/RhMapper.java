package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Rh;
import com.qintess.GerDemanda.service.dto.RhDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface RhMapper extends EntityMapper<RhDTO, Rh>{
}

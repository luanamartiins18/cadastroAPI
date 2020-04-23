package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.VersaoGuia;
import com.qintess.GerDemanda.service.dto.VersaoGuiaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface VersaoGuiaMapper extends EntityMapper<VersaoGuiaDTO, VersaoGuia> {
}
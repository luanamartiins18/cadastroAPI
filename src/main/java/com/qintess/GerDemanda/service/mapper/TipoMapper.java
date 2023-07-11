package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Tipo;
import com.qintess.GerDemanda.service.dto.TipoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TipoMapper extends EntityMapper<TipoDTO, Tipo> {
}

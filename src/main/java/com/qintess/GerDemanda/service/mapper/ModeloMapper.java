package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Modelo;
import com.qintess.GerDemanda.service.dto.ModeloDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface ModeloMapper extends EntityMapper <ModeloDTO, Modelo>{
}

package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Perfil;
import com.qintess.GerDemanda.service.dto.PerfilDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PerfilMapper extends EntityMapper<PerfilDTO, Perfil> {
}
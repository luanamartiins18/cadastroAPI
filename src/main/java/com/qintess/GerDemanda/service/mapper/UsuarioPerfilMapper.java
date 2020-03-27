package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.UsuarioPerfil;
import com.qintess.GerDemanda.service.dto.UsuarioPerfilDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UsuarioPerfilMapper extends EntityMapper<UsuarioPerfilDTO, UsuarioPerfil> {
}
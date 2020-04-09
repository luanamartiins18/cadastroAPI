package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.UsuarioSigla;
import com.qintess.GerDemanda.service.dto.UsuarioSiglaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UsuarioSiglaMapper extends EntityMapper<UsuarioSiglaDTO, UsuarioSigla> {
}
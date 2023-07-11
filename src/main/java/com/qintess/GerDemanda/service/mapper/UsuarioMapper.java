package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UsuarioMapper extends EntityMapper<UsuarioDTO, Usuario> {

}
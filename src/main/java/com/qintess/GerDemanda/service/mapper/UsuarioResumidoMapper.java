package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.dto.UsuarioResumidoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface UsuarioResumidoMapper extends EntityMapper<UsuarioResumidoDTO, Usuario> {

    @Override
    @Mapping(target = "senha", ignore = true)
    UsuarioResumidoDTO toDto(Usuario entity);

}
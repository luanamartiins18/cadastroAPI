package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.dto.LoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {})
public interface LoginMapper extends EntityMapper<LoginDTO, Usuario>{


    @Override
    @Mapping(target = "senha", ignore = true)
    LoginDTO toDto(Usuario entity);
}

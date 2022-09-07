package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.HistoricoUsuario;
import com.qintess.GerDemanda.service.dto.HistoricoUsuarioDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HistoricoUsuarioMapper extends EntityMapper<HistoricoUsuarioDTO, HistoricoUsuario>{
}

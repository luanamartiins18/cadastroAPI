package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.service.dto.UsuarioOrdemFornecimentoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UsuarioOrdemFornecimentoMapper extends EntityMapper<UsuarioOrdemFornecimentoDTO, UsuarioOrdemFornecimento> {

}
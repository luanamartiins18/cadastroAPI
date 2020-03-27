package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Cargo;
import com.qintess.GerDemanda.model.UsuarioMensagem;
import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.service.dto.CargoDTO;
import com.qintess.GerDemanda.service.dto.UsuarioMensagemDTO;
import com.qintess.GerDemanda.service.dto.UsuarioOrdemFornecimentoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface UsuarioOrdemFornecimentoMapper extends EntityMapper<UsuarioOrdemFornecimentoDTO, UsuarioOrdemFornecimento> {


}
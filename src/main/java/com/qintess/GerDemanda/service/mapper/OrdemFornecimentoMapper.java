package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrdemFornecimentoMapper extends EntityMapper<OrdemFornecimentoDTO, OrdemFornecimento> {
}
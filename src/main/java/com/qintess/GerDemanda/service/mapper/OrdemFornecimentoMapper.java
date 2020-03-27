package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoDTO;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoResumidaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface OrdemFornecimentoMapper extends EntityMapper<OrdemFornecimentoDTO, OrdemFornecimento> {
}

package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoResumidaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface OrdemFornecimentoMapper extends EntityMapper<OrdemFornecimentoResumidaDTO, OrdemFornecimento> {
    @Mapping(target = "situacaoUsu.id", source = "fk_situacao_usu")
    OrdemFornecimento toEntity(OrdemFornecimentoResumidaDTO dto);

    @Mapping(target = "fk_situacao_usu", source = "situacaoUsu.id")
    OrdemFornecimentoResumidaDTO toDto(OrdemFornecimento entity);
}

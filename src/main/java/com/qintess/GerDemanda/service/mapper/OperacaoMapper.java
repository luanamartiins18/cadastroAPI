 package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Operacao;
import com.qintess.GerDemanda.service.dto.OperacaoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OperacaoMapper extends EntityMapper<OperacaoDTO, Operacao>  {
}

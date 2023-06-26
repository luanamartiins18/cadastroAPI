package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Proposta;
import com.qintess.GerDemanda.service.dto.PropostaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProspostaMapper  extends EntityMapper<PropostaDTO, Proposta>{
}

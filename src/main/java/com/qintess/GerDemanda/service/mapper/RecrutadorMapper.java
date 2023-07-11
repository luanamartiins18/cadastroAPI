package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Recrutador;
import com.qintess.GerDemanda.service.dto.RecrutadorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface RecrutadorMapper  extends EntityMapper<RecrutadorDTO, Recrutador> {
}

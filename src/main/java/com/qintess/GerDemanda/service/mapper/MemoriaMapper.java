package com.qintess.GerDemanda.service.mapper;

;
import com.qintess.GerDemanda.model.Memoria;
import com.qintess.GerDemanda.service.dto.MemoriaDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface MemoriaMapper extends EntityMapper <MemoriaDTO, Memoria>{
}
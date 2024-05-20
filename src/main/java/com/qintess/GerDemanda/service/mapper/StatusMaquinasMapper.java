package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.StatusMaquina;
import com.qintess.GerDemanda.service.dto.StatusMaquinaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface StatusMaquinasMapper extends EntityMapper<StatusMaquinaDTO, StatusMaquina>{
}

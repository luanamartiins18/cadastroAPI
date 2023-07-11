package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Equipamento;
import com.qintess.GerDemanda.service.dto.EquipamentoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EquipamentoMapper extends EntityMapper <EquipamentoDTO, Equipamento>{
}
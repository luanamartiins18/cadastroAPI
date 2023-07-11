package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Cargo;
import com.qintess.GerDemanda.service.dto.CargoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CargoMapper extends EntityMapper<CargoDTO, Cargo> {
}
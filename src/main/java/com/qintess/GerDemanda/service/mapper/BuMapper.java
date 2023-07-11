package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Bu;
import com.qintess.GerDemanda.service.dto.BuDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface BuMapper extends EntityMapper<BuDTO, Bu>  {
}

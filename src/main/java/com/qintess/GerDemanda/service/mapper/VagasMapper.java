package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Vagas;
import com.qintess.GerDemanda.service.dto.VagasDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface VagasMapper extends EntityMapper<VagasDTO, Vagas>{
}

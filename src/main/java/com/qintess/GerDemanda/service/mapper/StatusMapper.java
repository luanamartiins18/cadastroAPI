package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Status;
import com.qintess.GerDemanda.service.dto.StatusDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface StatusMapper extends EntityMapper<StatusDTO, Status> {
}

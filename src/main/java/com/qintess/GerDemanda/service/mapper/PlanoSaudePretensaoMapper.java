package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.PlanoSaudePretensao;
import com.qintess.GerDemanda.service.dto.PlanoSaudePretensaoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PlanoSaudePretensaoMapper extends EntityMapper<PlanoSaudePretensaoDTO, PlanoSaudePretensao> {
}

package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.PlanoSaude;
import com.qintess.GerDemanda.service.dto.PlanoSaudeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PlanoSaudeMapper  extends EntityMapper<PlanoSaudeDTO, PlanoSaude> {
}

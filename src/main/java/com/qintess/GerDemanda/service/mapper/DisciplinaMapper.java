package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Disciplina;
import com.qintess.GerDemanda.service.dto.DisciplinaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface DisciplinaMapper extends EntityMapper<DisciplinaDTO, Disciplina> {

    @Override
    @Mapping(target = "perfil.descricao", source = "perfil")
    Disciplina toEntity(DisciplinaDTO dto);

    @Override
    @Mapping(target = "perfil", source = "perfil.descricao")
    DisciplinaDTO toDto(Disciplina entity);
}
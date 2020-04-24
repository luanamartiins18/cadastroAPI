package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.TarefaOf;
import com.qintess.GerDemanda.service.dto.TarefaOfDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface TarefaOfMapper extends EntityMapper<TarefaOfDTO, TarefaOf> {
    @Mapping(target = "usuarioOrdemFornecimento.usuario.id", source = "usu")
    @Mapping(target = "usuarioOrdemFornecimento.ordemFornecimento.id", source = "of")
    @Mapping(target = "num_tarefa", source = "numTarefa")
    @Mapping(target = "id", source = "idTrfOf")
    @Mapping(target = "itemGuia.id", source = "numItemGuia")
    @Mapping(target = "situacao.id", source = "situacao")
    @Mapping(target = "usuarioOrdemFornecimento.id", source = "usuOf")
    TarefaOf toEntity(TarefaOfDTO dto);

    @Mapping(target = "usu", source = "usuarioOrdemFornecimento.usuario.id")
    @Mapping(target = "of", source = "usuarioOrdemFornecimento.ordemFornecimento.id")
    @Mapping(target = "numTarefa", source = "num_tarefa")
    @Mapping(target = "numItemGuia", source = "itemGuia.id")
    @Mapping(target = "situacao", source = "situacao.id")
    @Mapping(target = "idTrfOf", source = "id")
    @Mapping(target = "usuOf", source = "usuarioOrdemFornecimento.id")
    TarefaOfDTO toDto(TarefaOf entity);
}
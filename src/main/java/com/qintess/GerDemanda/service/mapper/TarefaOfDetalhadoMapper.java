package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.TarefaOf;
import com.qintess.GerDemanda.service.dto.TarefaOfDetalhadoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface TarefaOfDetalhadoMapper extends EntityMapper<TarefaOfDetalhadoDTO, TarefaOf> {

    @Mapping(target = "situacao.descricao", source = "situacao")
    @Mapping(target = "situacao.id", source = "fk_situacao")
    @Mapping(target = "usuarioOrdemFornecimento.id", source = "fk_of_usuario")
    @Mapping(target = "itemGuia.tarefaGuia.tarefa", source = "tarefa")
    @Mapping(target = "itemGuia.valor", source = "valor")
    @Mapping(target = "itemGuia.tarefaGuia.id", source = "idTrfGuia")
    @Mapping(target = "itemGuia.complexGuia.descricao", source = "complexidade")
    @Mapping(target = "itemGuia.tarefaGuia.disciplina.id", source = "disciplina")
    @Mapping(target = "itemGuia.componente", source = "componente")
    @Mapping(target = "itemGuia.id", source = "fk_item_guia")
    @Mapping(target = "num_tarefa", source = "numTarefa")
    @Mapping(target = "usuarioOrdemFornecimento.usuario.id", source = "usu")
    @Mapping(target = "usuarioOrdemFornecimento.ordemFornecimento.id", source = "of")
    TarefaOf toEntity(TarefaOfDetalhadoDTO dto);

    @Mapping(target = "situacao", source = "situacao.descricao")
    @Mapping(target = "fk_situacao", source = "situacao.id")
    @Mapping(target = "fk_of_usuario", source = "usuarioOrdemFornecimento.id")
    @Mapping(target = "tarefa", source = "itemGuia.tarefaGuia.tarefa")
    @Mapping(target = "valor", source = "itemGuia.valor")
    @Mapping(target = "idTrfGuia", source = "itemGuia.tarefaGuia.id")
    @Mapping(target = "complexidade", source = "itemGuia.complexGuia.descricao")
    @Mapping(target = "disciplina", source = "itemGuia.tarefaGuia.disciplina.id")
    @Mapping(target = "componente", source = "itemGuia.componente")
    @Mapping(target = "fk_item_guia", source = "itemGuia.id")
    @Mapping(target = "numTarefa", source = "num_tarefa")
    @Mapping(target = "usu", source = "usuarioOrdemFornecimento.usuario.id")
    @Mapping(target = "of", source = "usuarioOrdemFornecimento.ordemFornecimento.id")
    TarefaOfDetalhadoDTO toDto(TarefaOf entity);
}
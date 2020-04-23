package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoFiltradoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface OrdemFornecimentoFiltradoMapper extends EntityMapper<OrdemFornecimentoFiltradoDTO, UsuarioOrdemFornecimento> {
    @Override
    @Mapping(target = "ordemFornecimento.id", source = "idOf")
    @Mapping(target = "ordemFornecimento.sigla.descricao", source = "sigla")
    @Mapping(target = "ordemFornecimento.tema", source = "tema")
    @Mapping(target = "ordemFornecimento.ustiBB", source = "ustibbGenti")
    @Mapping(target = "ordemFornecimento.situacaoGenti.descricao", source = "situacaoGenti")
    @Mapping(target = "ordemFornecimento.situacaoUsu.descricao", source = "situacaoAlm")
    @Mapping(target = "dtCriacao", source = "dtEncaminhamento")
    @Mapping(target = "ordemFornecimento.numeroOFGenti", source = "numOF")
    @Mapping(target = "ordemFornecimento.dtAbertura", source = "dtAbertura")
    @Mapping(target = "ordemFornecimento.responsavelTecnico", source = "responsavelT")
    @Mapping(target = "ordemFornecimento.dtPrevisao", source = "dtPrevisao")
    @Mapping(target = "ordemFornecimento.gerenteTecnico", source = "gerenteT")
    UsuarioOrdemFornecimento toEntity(OrdemFornecimentoFiltradoDTO dto);

    @Mapping(target = "idOf", source = "ordemFornecimento.id")
    @Mapping(target = "sigla", source = "ordemFornecimento.sigla.descricao")
    @Mapping(target = "tema", source = "ordemFornecimento.tema")
    @Mapping(target = "ustibbGenti", source = "ordemFornecimento.ustiBB")
    @Mapping(target = "situacaoGenti", source = "ordemFornecimento.situacaoGenti.descricao")
    @Mapping(target = "situacaoAlm", source = "ordemFornecimento.situacaoUsu.descricao")
    @Mapping(target = "dtEncaminhamento", source = "dtCriacao")
    @Mapping(target = "numOF", source = "ordemFornecimento.numeroOFGenti")
    @Mapping(target = "dtAbertura", source = "ordemFornecimento.dtAbertura")
    @Mapping(target = "responsavelT", source = "ordemFornecimento.responsavelTecnico")
    @Mapping(target = "dtPrevisao", source = "ordemFornecimento.dtPrevisao")
    @Mapping(target = "gerenteT", source = "ordemFornecimento.gerenteTecnico")
    OrdemFornecimentoFiltradoDTO toDto(UsuarioOrdemFornecimento entity);

}
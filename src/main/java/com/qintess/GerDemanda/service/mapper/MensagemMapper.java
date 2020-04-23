package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.service.dto.MensagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface MensagemMapper extends EntityMapper<MensagemDTO, Mensagem> {
    @Mapping(target = "id", source = "idMsg")
    @Mapping(target = "dtExpiracao", source = "dtExpiracao")
    @Mapping(target = "dtCriacao", source = "dtCriacao")
    @Mapping(target = "corpo", source = "corpo")
    @Mapping(target = "sigla.descricao", source = "sigla")
    @Mapping(target = "titulo", source = "titulo")
    @Mapping(target = "responsavel.nome", source = "responsavel")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "tipoMensagem", source = "tpMsg")
    Mensagem toEntity(MensagemDTO dto);

    @Mapping(target = "idMsg", source = "id")
    @Mapping(target = "dtExpiracao", source = "dtExpiracao")
    @Mapping(target = "dtCriacao", source = "dtCriacao")
    @Mapping(target = "corpo", source = "corpo")
    @Mapping(target = "sigla", source = "sigla.descricao")
    @Mapping(target = "titulo", source = "titulo")
    @Mapping(target = "responsavel", source = "responsavel.nome")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "tpMsg", source = "tipoMensagem")
    MensagemDTO toDto(Mensagem entity);
}
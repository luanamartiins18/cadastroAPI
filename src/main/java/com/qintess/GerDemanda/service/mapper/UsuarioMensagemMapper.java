package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.UsuarioMensagem;
import com.qintess.GerDemanda.service.dto.UsuarioMensagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface UsuarioMensagemMapper extends EntityMapper<UsuarioMensagemDTO, UsuarioMensagem>  {

    @Override
    @Mapping(target = "mensagem.id", source = "idMsg")
    @Mapping(target = "mensagem.dtExpiracao", source = "dtExpiracao")
    @Mapping(target = "mensagem.dtCriacao", source = "dtCriacao")
    @Mapping(target = "mensagem.corpo", source = "corpo")
    @Mapping(target = "mensagem.sigla.descricao", source = "sigla")
    @Mapping(target = "mensagem.titulo", source = "titulo")
    @Mapping(target = "mensagem.responsavel.nome", source = "responsavel")
    @Mapping(target = "mensagem.status", source = "status")
    @Mapping(target = "mensagem.tipoMensagem", source = "tpMsg")
    @Mapping(target = "id", source = "idUsuMsg")
    @Mapping(target = "mensagem.responsavel.id", source = "idU")
    @Mapping(target = "usuarioMens.nome", source = "nomeUsu")
    UsuarioMensagem toEntity(UsuarioMensagemDTO dto);

    @Override
    @Mapping(target = "idMsg", source = "mensagem.id")
    @Mapping(target = "dtExpiracao", source = "mensagem.dtExpiracao")
    @Mapping(target = "dtCriacao", source = "mensagem.dtCriacao")
    @Mapping(target = "corpo", source = "mensagem.corpo")
    @Mapping(target = "sigla", source = "mensagem.sigla.descricao")
    @Mapping(target = "titulo", source = "mensagem.titulo")
    @Mapping(target = "responsavel", source = "mensagem.responsavel.nome")
    @Mapping(target = "status", source = "mensagem.status")
    @Mapping(target = "tpMsg", source = "mensagem.tipoMensagem")
    @Mapping(target = "idUsuMsg", source = "id")
    @Mapping(target = "idU", source = "mensagem.responsavel.id")
    @Mapping(target = "nomeUsu", source = "usuarioMens.nome")
    UsuarioMensagemDTO toDto(UsuarioMensagem entity);
}

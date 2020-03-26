package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.model.UsuarioMensagem;
import com.qintess.GerDemanda.service.dto.UsuarioMensagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface UsuarioMensagemMapper extends EntityMapper<UsuarioMensagemDTO, UsuarioMensagem>  {
    @Mapping(target = "mensagem.id", source = "idMsg")
    @Mapping(target = "mensagem.dtExpiracao", source = "dtExpiracao")
    @Mapping(target = "mensagem.dtCriacao", source = "dtCriacao")
    @Mapping(target = "mensagem.corpo", source = "corpo")
    @Mapping(target = "mensagem.sigla.descricao", source = "sigla")
    @Mapping(target = "mensagem.titulo", source = "titulo")
    @Mapping(target = "mensagem.responsavel.nome", source = "responsavel")
    @Mapping(target = "mensagem.status", source = "status")
    @Mapping(target = "mensagem.tipoMensagem", source = "tpMsg")
    @Mapping(target = "usuarioMens.id", source = "idUsuMsg")
    UsuarioMensagem toEntity(UsuarioMensagemDTO dto);

    @Mapping(target = "idMsg", source = "mensagem.id")
    @Mapping(target = "dtExpiracao", source = "mensagem.dtExpiracao")
    @Mapping(target = "dtCriacao", source = "mensagem.dtCriacao")
    @Mapping(target = "corpo", source = "mensagem.corpo")
    @Mapping(target = "sigla", source = "mensagem.sigla.descricao")
    @Mapping(target = "titulo", source = "mensagem.titulo")
    @Mapping(target = "responsavel", source = "mensagem.responsavel.nome")
    @Mapping(target = "status", source = "mensagem.status")
    @Mapping(target = "tpMsg", source = "mensagem.tipoMensagem")
    @Mapping(target = "idUsuMsg", source = "usuarioMens.id")
    UsuarioMensagemDTO toDto(UsuarioMensagem entity);
}

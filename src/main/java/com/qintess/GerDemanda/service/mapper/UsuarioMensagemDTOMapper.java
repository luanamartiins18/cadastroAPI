package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.model.UsuarioMensagem;
import com.qintess.GerDemanda.service.dto.UsuarioMensagemDTO;

import java.util.Objects;

public abstract class UsuarioMensagemDTOMapper {

    public static UsuarioMensagem dtoToModel(UsuarioMensagemDTO dto) {
        return UsuarioMensagem.builder()
                .mensagem(
                        Mensagem.builder()
                                .id(dto.getIdMsg())
                                .dtExpiracao(dto.getDtExpiracao())
                                .dtCriacao(dto.getDtCriacao())
                                .corpo(dto.getCorpo())
                                .tipoMensagem(dto.getTpMsg())
                                .sigla(Sigla.builder()
                                        .descricao(dto.getSigla())
                                        .build())
                                .titulo(dto.getTitulo())
                                .responsavel(Usuario.builder()
                                        .nome(dto.getResponsavel())
                                        .build())
                                .status(dto.getStatus())
                                .build())
                .usuarioMens(Usuario.builder().id(dto.getIdUsuMsg()).build())
                .build();
    }

    public static UsuarioMensagemDTO modelToDto(UsuarioMensagem model) {
        return UsuarioMensagemDTO.builder()
                .idMsg(model.getMensagem().getId())
                .dtExpiracao(model.getMensagem().getDtExpiracao())
                .corpo(model.getMensagem().getCorpo())
                .tpMsg(model.getMensagem().getTipoMensagem())
                .sigla(Objects.nonNull(model.getMensagem().getSigla()) ? model.getMensagem().getSigla().getDescricao() : null)
                .idUsuMsg(model.getMensagem().getId())
                .dtCriacao(model.getMensagem().getDtCriacao())
                .titulo(model.getMensagem().getTitulo())
                .responsavel(Objects.nonNull(model.getMensagem().getResponsavel()) ? model.getMensagem().getResponsavel().getNome() : null)
                .status(model.getMensagem().getStatus())
                .build();
    }

}

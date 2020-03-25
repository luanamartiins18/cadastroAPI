package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.dto.MensagemDTO;

import java.util.ArrayList;
import java.util.Date;

public abstract class MensagemMapper {

    public static Mensagem dtoToModel(MensagemDTO dto) {
        return Mensagem.builder()
                .corpo(dto.getCorpo())
                .responsavel(Usuario.builder().id(dto.getIdResp()).build())
                .dtExpiracao(dto.getDtExp())
                .sigla(Sigla.builder().id(dto.getIdSigla()).build())
                .titulo(dto.getTitulo())
                .dtCriacao(new Date())
                .listaUsuarios(new ArrayList<>())
                .status(1)
                .build();
    }

    public static MensagemDTO modelToDto(Mensagem model) {
        return MensagemDTO.builder()
                .corpo(model.getCorpo())
                .idResp(model.getResponsavel().getId())
                .dtExp(model.getDtExpiracao())
                .idSigla(model.getSigla().getId())
                .titulo(model.getTitulo())
                .build();
    }
}

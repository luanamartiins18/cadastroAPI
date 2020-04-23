package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.dto.MensagemInDTO;
import com.qintess.GerDemanda.service.util.DateUtil;

import java.util.ArrayList;
import java.util.Objects;

public abstract class MensagemBuilder {

    public static Mensagem toDtoBuild(MensagemInDTO dto) {
        return Mensagem.builder()
                .corpo(dto.getCorpo())
                .responsavel(Usuario.builder().id(dto.getIdResp()).build())
                .dtExpiracao(dto.getDtExp())
                .sigla(Objects.nonNull(dto.getIdSigla()) ? Sigla.builder().id(dto.getIdSigla()).build() : null)
                .titulo(dto.getTitulo())
                .dtCriacao(DateUtil.getCurrentDateTime())
                .listaUsuarios(new ArrayList<>())
                .status(1)
                .build();
    }
}
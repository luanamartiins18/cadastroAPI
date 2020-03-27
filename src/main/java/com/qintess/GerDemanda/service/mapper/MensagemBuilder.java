package com.qintess.GerDemanda.service.mapper;

import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.dto.MensagemInDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public abstract class MensagemBuilder {

    public static Mensagem toDtoBuild(MensagemInDTO dto) {
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
}

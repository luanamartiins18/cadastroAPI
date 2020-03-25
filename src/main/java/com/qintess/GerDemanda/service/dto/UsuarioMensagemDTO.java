package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qintess.GerDemanda.model.UsuarioMensagem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioMensagemDTO {
    private Integer idMsg;
    private String corpo;
    private String tpMsg;
    private String sigla;
    private Integer idUsuMsg;
    private String titulo;
    private String responsavel;
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dtExpiracao;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dtCriacao;
}

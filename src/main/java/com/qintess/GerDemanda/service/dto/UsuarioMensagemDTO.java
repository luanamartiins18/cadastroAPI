package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qintess.GerDemanda.model.UsuarioMensagem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioMensagemDTO {
    private Integer id; // idUM virou Id
    private Integer idMsg;
    private String corpo;
    private String tpMsg;
    private String sigla;
    private Integer idUsuMsg;
    private String titulo;
    private String responsavel;
    private Integer status;
    private String nomeUsu;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtExpiracao;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtCriacao;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  dtLeitura;
}

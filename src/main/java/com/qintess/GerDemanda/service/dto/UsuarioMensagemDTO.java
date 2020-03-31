package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioMensagemDTO {
    private Integer idUsuMsg;
    private Integer idMsg;
    private Integer idU;
    private String corpo;
    private String tpMsg;
    private String sigla;
    private String titulo;
    private String responsavel;
    private Integer status;
    private String nomeUsu;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtExpiracao;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtCriacao;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtLeitura;
}

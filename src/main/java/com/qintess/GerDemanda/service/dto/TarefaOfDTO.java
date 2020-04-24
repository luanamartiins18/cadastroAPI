package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaOfDTO {

    private Integer idTrfOf;
    private String historia;
    private String sprint;
    private String observacao;
    private String artefato;
    private Integer usu;
    private Integer of;
    private Integer numItemGuia;
    private Date dtInclusao;
    private Date dtAlteracao;
    private Integer situacao;
    private String numTarefa;
    private String perfil;
    private Integer quantidade;
    private Integer usuOf;
}
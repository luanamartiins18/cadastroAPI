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
public class TarefaOfDetalhadoDTO {
    private String historia;
    private String observacao;
    private Integer fk_item_guia;
    private String situacao;
    private Date dtInclusao;
    private String tarefa;
    private String sprint;
    private Double valor;
    private Integer idTrfGuia;
    private Date dtAlteracao;
    private String perfil;
    private String artefato;
    private Integer fk_of_usuario;
    private String complexidade;
    private Integer disciplina;
    private String numTarefa;
    private Integer id;
    private String componente;
    private Integer quantidade;
    private Integer fk_situacao;
    private Integer usu;
    private Integer of;

}
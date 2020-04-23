package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdemFornecimentoDTO {
    private Integer id;
    private String numeroOF;
    private String numeroOFGenti;
    private String fabrica;
    private String tema;
    private boolean agil;
    private double ustiBB;
    private String uor;
    private String demanda;
    private String acao;
    private String tipo;
    private String cdTI;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtAbertura;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtPrevisao;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtEntrega;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtDevolvida;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtRecusa;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtAceite;
    private String responsavelTecnico;
    private String gerenteTecnico;
    private SiglaDTO sigla;
    private SituacaoDTO situacaoGenti;
    private SituacaoDTO situacaoUsu;
    private String referencia;
    private List<UsuarioOrdemFornecimentoDTO> listaUsuarios;
}
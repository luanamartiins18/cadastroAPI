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
public class OrdemFornecimentoFiltradoDTO {
    private Integer idOf;
    private String sigla;
    private String tema;
    private double ustibbGenti;
    private String situacaoGenti;
    private String situacaoAlm;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtEncaminhamento;
    private String numOF;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtAbertura;
    private String responsavelT;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtPrevisao;
    private String gerenteT;

}
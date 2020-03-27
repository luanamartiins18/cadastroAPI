package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdemFornecimentoDetalhadoDTO {

    private Integer id;
    private String numeroOFGenti;
    private String referencia;
    private String responsavelTecnico;
    private String gerenteTecnico;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtAbertura;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtPrevisao;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtEntrega;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtAceite;
    private String sigla;
    private String situacaoGenti;
    private String situacaoAlm;
    private Double valorExecutado;
    private Double valorPlanejado;

    public OrdemFornecimentoDetalhadoDTO(Object[] obj) {
        this.id = (Integer) obj[0];
        this.numeroOFGenti = (String) obj[1];
        this.referencia = (String) obj[2];
        this.responsavelTecnico = (String) obj[3];
        this.gerenteTecnico = (String) obj[4];
        this.dtAbertura = (LocalDateTime) obj[5];
        this.dtPrevisao = (LocalDateTime) obj[6];
        this.dtEntrega = (LocalDateTime) obj[7];
        this.dtAceite = (LocalDateTime) obj[8];
        this.sigla = (String) obj[9];
        this.situacaoGenti = (String) obj[10];
        this.situacaoAlm = (String) obj[11];
        this.valorExecutado = (Double) obj[12];
        this.valorPlanejado = (Double) obj[13];
    }
}





























package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdemFornecimentoDTO {

    private Integer id;
    private String numeroOFGenti;
    private String referencia;
    private String responsavelTecnico;
    private String gerenteTecnico;
    private Calendar dtAbertura;
    private Calendar dtPrevisao;
    private Calendar dtEntrega;
    private Calendar dtAceite;
    private String sigla;
    private String situacaoGenti;
    private String situacaoAlm;
    private Double valorExecutado;
    private Double valorPlanejado;

    public OrdemFornecimentoDTO(Object[] obj) {
        this.id = (Integer) obj[0];
        this.numeroOFGenti = (String) obj[1];
        this.referencia = (String) obj[2];
        this.responsavelTecnico = (String) obj[3];
        this.gerenteTecnico = (String) obj[4];
        this.dtAbertura = (Calendar) obj[5];
        this.dtPrevisao = (Calendar) obj[6];
        this.dtEntrega = (Calendar) obj[7];
        this.dtAceite = (Calendar) obj[8];
        this.sigla = (String) obj[9];
        this.situacaoGenti = (String) obj[10];
        this.situacaoAlm = (String) obj[11];
        this.valorExecutado = (Double) obj[12];
        this.valorPlanejado = (Double) obj[13];
    }
}





























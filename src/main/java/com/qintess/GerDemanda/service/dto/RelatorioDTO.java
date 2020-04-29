package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelatorioDTO {

    String numero_of;
    String colaborador;
    String status_of;
    Double valor_ustibb;
    Double valor;
    String referencia;
    String sigla;
    Long qtd;

    public RelatorioDTO(
            String numero_of,
            String colaborador,
            String status_of,
            Double valor_ustibb,
            String referencia,
            String sigla,
            Long qtd
    ) {
        this.numero_of = numero_of;
        this.colaborador = colaborador;
        this.status_of = status_of;
        this.valor_ustibb = valor_ustibb;
        this.referencia = referencia;
        this.sigla = sigla;
        this.qtd = qtd;
    }

    public RelatorioDTO(
            String numero_of,
            String colaborador,
            String status_of,
            Double valor_ustibb,
            String referencia,
            String sigla
    ) {
        this.numero_of = numero_of;
        this.colaborador = colaborador;
        this.status_of = status_of;
        this.valor_ustibb = valor_ustibb;
        this.referencia = referencia;
        this.sigla = sigla;
    }


    public RelatorioDTO(
            String numero_of,
            String status_of,
            Double valor_ustibb,
            String referencia,
            String sigla

    ) {
        this.numero_of = numero_of;
        this.status_of = status_of;
        this.valor_ustibb = valor_ustibb;
        this.referencia = referencia;
        this.sigla = sigla;
    }
}
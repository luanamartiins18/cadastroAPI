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

    Integer numero_of;
    String colaborador;
    String status_of;
    Double valor_ustibb;
    Double valor;
    String referencia;
    String sigla;
    Integer idOf;


    public RelatorioDTO(Object[] obj) {
        this.numero_of = (Integer) obj[0];
        this.colaborador = (String) obj[1];
        this.status_of = (String) obj[2];
        this.valor_ustibb = (Double) obj[3];
        this.valor = (Double) obj[4];
        this.referencia = (String) obj[5];
        this.sigla = (String) obj[6];
        this.idOf = (Integer) obj[7];
    }
}
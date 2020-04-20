package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdensConcluidasDTO {
    String referencia;
    Integer num_of;
    String sigla;
    String responsavel_t;
    String gerente_t;
    Double ustibb;
    Double valor;
    String status;
    Integer idOf;

    public OrdensConcluidasDTO(Object[] obj) {
        this.referencia = (String) obj[0];
        this.num_of = Integer.parseInt((String) obj[1]);
        this.sigla = (String) obj[2];
        this.responsavel_t = (String) obj[3];
        this.gerente_t = (String) obj[4];
        this.ustibb = (Double) obj[5];
        this.valor = (Double) obj[6];
        this.status = (String) obj[7];
        this.idOf = (Integer) obj[8];
    }
}
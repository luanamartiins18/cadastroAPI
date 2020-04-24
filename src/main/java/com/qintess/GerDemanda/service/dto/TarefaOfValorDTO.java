package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaOfValorDTO {
    private Double valorExecutado;
    private Double valorExecutadoTotal;
    private Double valorPlanejadoTotal;
    private Double valorPlanejado;
}
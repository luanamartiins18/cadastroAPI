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
    private Double valorExecutado = 0.0;
    private Double valorExecutadoTotal = 0.0;
    private Double valorPlanejadoTotal = 0.0;
    private Double valorPlanejado = 0.0;
}
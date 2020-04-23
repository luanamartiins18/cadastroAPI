package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdemFornecimentoResumidaDTO {
    private Integer fk_situacao_usu;
    private String referencia;
}
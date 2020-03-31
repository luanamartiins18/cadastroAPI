package com.qintess.GerDemanda.service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ContratoDTO {
    private Integer id;
    private String descricao;
}

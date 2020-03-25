package com.qintess.GerDemanda.service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerfilDTO {
    private int id;
    private String descricao;
}
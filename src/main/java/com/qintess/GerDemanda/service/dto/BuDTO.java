package com.qintess.GerDemanda.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuDTO {
    private Integer id;
    private String descricao;
}

package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdemFornecimentoInDTO {
    private List<Integer> usu;
    private Integer sit;
    private Integer of;
    private String ref;
}
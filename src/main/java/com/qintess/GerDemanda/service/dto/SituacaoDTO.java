package com.qintess.GerDemanda.service.dto;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituacaoDTO {
	private Integer id;
	private String descricao;
}
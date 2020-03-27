package com.qintess.GerDemanda.service.dto;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituacaoDTO {
	private int id;
	private String descricao;
}
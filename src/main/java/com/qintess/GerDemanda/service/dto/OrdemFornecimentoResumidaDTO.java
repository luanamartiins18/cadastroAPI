package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.model.Situacao;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdemFornecimentoResumidaDTO {
	private Integer fk_situacao_usu;
	private String referencia;
}





























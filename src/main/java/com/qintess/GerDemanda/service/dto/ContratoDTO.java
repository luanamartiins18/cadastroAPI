package com.qintess.GerDemanda.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;


import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class ContratoDTO {

     private Integer id;

     private ClienteDTO cliente;

     private OperacaoDTO operacao;

     private CentroCustoDTO centro;

     private DemandaDTO demanda;

    @NotNull(message = "O campo codigoRe é obrigatório!")
     @Length(min=6,max=12, message = "O campo Matricula precisa ter no máximo 8 caracteres.")
     private String codigoRe;
}

package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropostaDTO {

    private Integer id;

    private CandidatosDTO candidatos;

    @NotNull(message = "O campo Remuneração é obrigatório!")
    private String  remuneracao;

    private StatusCandidatoDTO status_candidatos;

    private String flash;

    private boolean vale_alimentacao;

    private boolean plano_saude;

    private boolean plano_odonto;

    private boolean seguro_vida;

    private boolean vale_refeicao;



}

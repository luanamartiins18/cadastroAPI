package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatosDTO {

    private Integer id;

    @NotNull(message = "O campo candidato é obrigatório!")
    @Length(min=6,max=99,message = "O campo nome precisa ter no mínimo 3 caracteres e no máximo 99.")
    private String candidatos;

    @CPF(message = "Informe um CPF válido!")
    @NotNull
    private String cpf;

    @NotNull(message = "O campo RG é obrigatório!")
    private String rg;

    @Email(message = "Por favor informe um e-mail válido!")
    @NotNull(message = "O campo e-mail é obrigatório!")
    @Length(min=6,max=80,message = "O campo e-mail precisa ter no mínimo 6 caracteres e no máximo 80.")
    private String email;


    @NotNull(message = "O campo Telefone é obrigatório!")
    @Length(min=11,max=15,message = "O campo telefone precisa ter no mínimo 11 caracteres.")
    private String telefone;

    @NotNull(message = "O campo Remuneração é obrigatório!")
    private String remuneracao_atual;

    private String vale_alimentacao_atual;

    private String vale_refeicao_atual;

    private String flash_atual;

    private String cesta_atual;

    private String bonus_atual;

    @NotNull(message = "O campo Remuneração é obrigatório!")
    private String remuneracao_pretensao;

    private String vale_alimentacao_pretensao;

    private String vale_refeicao_pretensao;

    private String flash_pretensao;

    private String cesta_pretensao;

    private String bonus_pretensao;

    private String arquivos;

    private StatusCandidatoDTO status_candidato;

    private VagasDTO vagas;

    private String observacao;

    private String motivo;

    private PlanoSaudeDTO planoSaude;

    private PlanoSaudePretensaoDTO planoPretensao;

    private CurriculoDTO curriculo;
}

package com.qintess.GerDemanda.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RhDTO {

    private Integer id;

    @NotNull(message = "O campo candidato é obrigatório!")
    @Length(min=6,max=99,message = "O campo nome precisa ter no mínimo 3 caracteres e no máximo 99.")
    private String candidato;

    @CPF(message = "Informe um CPF válido!")
    @NotNull
    private String cpf;

    @NotNull(message = "O campo RG é obrigatório!")
    private String rg;

    @NotNull(message = "O campo Número zoro é obrigatório!")
    private String numero_zoro;

    @NotNull(message = "O campo telefone é obrigatório!")
    @Length(min=11,max=15,message = "O campo telefone precisa ter no mínimo 11 caracteres.")
    private String telefone;

    @Email(message = "Por favor informe um e-mail válido!")
    @NotNull(message = "O campo e-mail é obrigatório!")
    @Length(min=6,max=80,message = "O campo e-mail precisa ter no mínimo 6 caracteres e no máximo 80.")
    private String email;

    private String vale_alimentacao;

    private String vale_refeicao;

    @NotNull(message = "O campo Remuneração é obrigatório!")
    private String remuneracao;

    private String bonus;

    @NotNull(message = "O campo Plano de saúde é obrigatório!")
    private String plano_saude;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_inicio;

    private String cesta;

    private RecrutadorDTO recrutador;

    private CargoDTO cargo;

    private EspecialidadeDTO especialidade;

    private BuDTO bu;

    private OperacaoDTO operacao;

    private String flash;

    private StatusDTO status;

    private EtapaDTO etapa;

}

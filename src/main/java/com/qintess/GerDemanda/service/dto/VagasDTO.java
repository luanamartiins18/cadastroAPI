package com.qintess.GerDemanda.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VagasDTO {

    private Integer id;

    @NotNull(message = "O campo qualitor é obrigatório!")
    private String qualitor;


    private String vale_alimentacao;

    private String vale_refeicao;

    @NotNull(message = "O campo Remuneração é obrigatório!")
    private String remuneracao;

    private String bonus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_final;

    private String cesta;

    private RecrutadorDTO recrutador;

    private CargoDTO cargo;

    private EspecialidadeDTO especialidade;

    private BuDTO bu;

    private OperacaoDTO operacao;

    private PlanoSaudeDTO planoSaude;

    private String flash;

    private StatusDTO status;

    private EtapaDTO etapa;

}

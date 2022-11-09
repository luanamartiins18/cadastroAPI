package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;


import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class MaquinasDTO {

    private Integer id;

    private ModeloDTO modelo;

    private EquipamentoDTO equipamento;

    private MemoriaDTO memoria;

    @NotNull(message = "O campo codigoRe é obrigatório!")
    @Length(min=6,max=12, message = "O campo Matricula precisa ter no máximo 6 caracteres.")
    private String codigoRe;

    @NotNull(message = "O campo tag é obrigatório!")
    private String tag;

    @NotNull(message = "O campo Patrimônio é obrigatório!")
    private String patrimonio;

}
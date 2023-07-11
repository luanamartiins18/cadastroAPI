package com.qintess.GerDemanda.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuncaoDTO {
    private Integer id;

    private TipoDTO tipo;

    private BuDTO bu;

    private CargoDTO cargo;


    @NotNull(message = "O campo codigoRe é obrigatório!")
    @Length(min=6,max=12, message = "O campo Matricula precisa ter no máximo 8 caracteres.")
    private String codigoRe;
}

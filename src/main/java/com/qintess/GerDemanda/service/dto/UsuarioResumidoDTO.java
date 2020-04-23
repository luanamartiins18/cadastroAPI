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
public class UsuarioResumidoDTO {
    private Integer id;
    private String nome;
    private String email;
    @NotNull(message = "O campo codigoRe é obrigatório!")
    @Length(min=6,max=12, message = "O campo codigoRe precisa ter no máximo 12 caracteres.")
    private String codigoRe;
    private String codigoBB;
    private CargoDTO cargo;
    private Boolean primeiroAcesso;
    @NotNull(message = "O campo senha é obrigatório!")
    @Length(min=6,max=80, message = "O campo senha precisa ter no mínimo 6 caracteres.")
    private String senha;
    private String status;
}
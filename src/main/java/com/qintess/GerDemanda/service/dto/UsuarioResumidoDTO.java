package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResumidoDTO {
    private Integer id;
    private String nome;
    private String email;
    private String codigoRe;
    private String codigoBB;
    private CargoDTO cargo;
    private Boolean primeiroAcesso;
    private String senha;
}

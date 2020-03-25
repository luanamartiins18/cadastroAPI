package com.qintess.GerDemanda.service.dto;

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
    private CargoDTO cargo;

}

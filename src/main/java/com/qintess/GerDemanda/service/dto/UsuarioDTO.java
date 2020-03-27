package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String codigoRe;
    private String codigoBB;
    private String empresa;
    private Integer demanda;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nascimento;
    private String status;
    private ContratoDTO contrato;
    private CargoDTO cargo;
    private List<UsuarioSiglaDTO> listaSiglas;
    private List<UsuarioPerfilDTO> listaPerfil;
    private String celular;
}

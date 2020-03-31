package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private Integer id;

    @NotNull(message = "O campo nome é obrigatório!")
    @Length(min=3,max=99,message = "O campo nome precisa ter no mínimo 3 caracteres e no máximo 99.")
    private String nome;

    @Email(message = "O campo email é obrigatório!")
    @NotNull
    @Length(min=6,max=80,message = "O campo email precisa ter no mínimo 6 caracteres e no máximo 80.")
    private String email;

    @CPF(message = "Informe um cpf válido!")
    @NotNull
    private String cpf;

    @NotNull(message = "O campo codigoRe é obrigatório!")
    @Length(max=12, message = "O campo codigoRe precisa ter no mínimo 12 caracteres.")
    private String codigoRe;

    @NotNull(message = "O campo codigoBB é obrigatório!")
    @Length(max=12, message = "O campo codigoBB precisa ter no mínimo 12 caracteres.")
    private String codigoBB;

    @NotNull(message = "O campo empresa é obrigatório!")
    @Length(min=2,max=25, message = "O campo empresa precisa ter no mínimo 2 caracteres e no máximo 25.")
    private String empresa;

    @NotNull(message = "O campo demanda é obrigatório!")
    private Integer demanda;

    @NotNull(message = "O campo data de nascimento é obrigatório!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nascimento;

    private String status;
    private ContratoDTO contrato;
    private CargoDTO cargo;

    @NotEmpty
    private List<UsuarioSiglaDTO> listaSiglas;
    private List<UsuarioPerfilDTO> listaPerfil;

    @NotNull(message = "O campo telefone é obrigatório!")
    @Length(min=14,max=15,message = "O campo telefone precisa ter no mínimo 14 caracteres e no máximo 15.")
    private String celular;
}

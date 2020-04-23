package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private Integer id;

    @NotNull(message = "O campo nome é obrigatório!")
    @Length(min=6,max=99,message = "O campo nome precisa ter no mínimo 3 caracteres e no máximo 99.")
    private String nome;

    @Email(message = "Por favor informe um e-mail válido!")
    @NotNull(message = "O campo e-mail é obrigatório!")
    @Length(min=6,max=80,message = "O campo e-mail precisa ter no mínimo 6 caracteres e no máximo 80.")
    private String email;

    @CPF(message = "Informe um CPF válido!")
    @NotNull
    private String cpf;

    @NotNull(message = "O campo telefone é obrigatório!")
    @Length(min=14,max=15,message = "O campo telefone precisa ter no mínimo 14 caracteres e no máximo 15.")
    private String celular;

    @NotNull(message = "O campo cargo é obrigatório!")
    private CargoDTO cargo;

    @NotNull(message = "O campo data de nascimento é obrigatório!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date nascimento;

    @NotNull(message = "O campo codigoRe é obrigatório!")
    @Length(min=6,max=12, message = "O campo codigoRe precisa ter no máximo 12 caracteres.")
    private String codigoRe;

    @NotNull(message = "O campo codigoBB é obrigatório!")
    @Length(min=6,max=12, message = "O campo codigoBB precisa ter no máximo 12 caracteres.")
    private String codigoBB;

    @NotEmpty(message = "O campo sigla é obrigatório!")
    private List<SiglaDTO> listaSiglas;

    @NotEmpty(message = "O campo perfil é obrigatório!")
    private List<PerfilDTO> listaPerfil;

    @JsonIgnore
    private String senha;
    private String primeiroAcesso;
    private ContratoDTO contrato;
    private String status;
    private String empresa;
    private Integer demanda;

}
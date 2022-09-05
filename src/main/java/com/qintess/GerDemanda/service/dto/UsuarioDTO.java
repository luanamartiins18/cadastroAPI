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
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private Integer id;

    @NotNull(message = "O campo nome é obrigatório!")
    @Length(min=6,max=99,message = "O campo nome precisa ter no mínimo 3 caracteres e no máximo 99.")
    private String nome;

    @CPF(message = "Informe um CPF válido!")
    @NotNull
    private String cpf;

    @NotNull(message = "O campo RG é obrigatório!")
    private String rg;

    @NotNull(message = "O campo Orgão emissor é obrigatório!")
    private String org_emissor;


    @NotNull(message = "O campo data emissão é obrigatório!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_emissao;

    @NotNull(message = "O campo data de nascimento é obrigatório!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_nascimento;

    @NotNull(message = "O campo endereço é obrigatório!")
    private String endereco;

    @NotNull(message = "O campo Nr é obrigatório!")
    private String numero;

    @NotNull(message = "O campo cep é obrigatório!")
    private String cep;

    @NotNull(message = "O campo Celular é obrigatório!")
    @Length(min=11,max=15,message = "O campo celular precisa ter no mínimo 11 caracteres.")
    private String celular;

    @Email(message = "Por favor informe um e-mail válido!")
    @NotNull(message = "O campo e-mail é obrigatório!")
    @Length(min=6,max=80,message = "O campo e-mail precisa ter no mínimo 6 caracteres e no máximo 80.")
    private String email;

    @NotNull(message = "O campo codigoRe é obrigatório!")
    @Length(min=6,max=12, message = "O campo Matrciula precisa ter no máximo 8 caracteres.")
    private String codigoRe;

    @NotNull(message = "O campo cidade é obrigatório!")
    private String cidade;

    @NotNull(message = "O campo uf é obrigatório!")
    private String uf;

    @NotNull(message = "O campo tipo é obrigatório!")
    private TipoDTO tipo;

    @NotNull(message = "O campo bu é obrigatório!")
    private BuDTO bu;

    @NotNull(message = "O campo cargo é obrigatório!")
    private CargoDTO cargo;


    @JsonIgnore
    private String senha;
    private String complemento;
    private String primeiroAcesso;
    private String status;


}
package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotNull(message = "O campo complemento é obrigatório!")
    private String complemento;

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

    private String status;

    private String tag;

    private String patrimonio;

    private TipoDTO tipo;

    private BuDTO bu;

    private CargoDTO cargo;

    private PerfilDTO perfil;

    private DemandaDTO demanda;

    private OperacaoDTO operacao;

    private ClienteDTO cliente;

    private CentroCustoDTO centro;

    private EquipamentoDTO equipamento;

    private ModeloDTO modelo;

    private MemoriaDTO memoria;


    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getPatrimonio() {
        return patrimonio;
    }
}
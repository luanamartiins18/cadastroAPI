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

    @Length(min=6,max=99,message = "O campo nome precisa ter no mínimo 3 caracteres e no máximo 99.")
    private String nome;

    @CPF(message = "Informe um CPF válido!")
    private String cpf;


    private String rg;


    private String org_emissor;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_emissao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_nascimento;

    private String endereco;

    private String numero;

    private String cep;

    private String complemento;

    @Length(min=11,max=15,message = "O campo celular precisa ter no mínimo 11 caracteres.")
    private String celular;

    @Email(message = "Por favor informe um e-mail válido!")

    @Length(min=6,max=80,message = "O campo e-mail precisa ter no mínimo 6 caracteres e no máximo 80.")
    private String email;

    @Length(min=6,max=80,message = "O campo e-mail precisa ter no mínimo 6 caracteres e no máximo 80.")
    private String emailPessoal;

    @Length(min=6,max=12, message = "O campo Matrciula precisa ter no máximo 8 caracteres.")
    private String codigoRe;

    private String cidade;

    private String uf;

    private String status;

    private String tag;

    private String patrimonio;

    private TipoDTO tipo;

    private BuDTO bu;

    private CargoDTO cargo;

    private PerfilDTO perfil;

    private ContratoDTO contrato;

    private EquipamentoDTO equipamento;

    private ModeloDTO modelo;

    private MemoriaDTO memoria;

    private String senha;
    private String primeiroAcesso;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_final;

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
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

    private String celular;

    @Email(message = "Por favor informe um e-mail válido!")
    private String email;

    @Email(message = "Por favor informe um e-mail válido!")
    private String email_pessoal;

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
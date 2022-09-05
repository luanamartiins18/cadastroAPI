package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column
    private String nome;

    @Column
    private String cpf;

    @Column
    private String rg;

    @Column
    private String org_emissor;

    @Temporal(TemporalType.DATE)
    private Date data_nascimento;

    @Temporal(TemporalType.DATE)
    private Date data_emissao;

    @Column
    private String endereco;

    @Column
    private String numero;

    @Column
    private String complemento;

    @Column
    private String cep;

    @Column
    private String celular;

    @Column
    private String email;

    @Column
    private String cidade;

    @Column
    private String uf;

    @JsonIgnore
    @Column
    private String senha;

    @Column(name = "codigo_re")
    private String codigoRe;

    @Column
    private String status;

    @Column(name = "primeiro_acesso")
    private Boolean primeiroAcesso;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_cargo")
    private Cargo cargo;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_tipo")
    private Tipo tipo;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_bu")
    private Bu bu;

}
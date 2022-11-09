package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column
    private String tag;

    @Column
    private String patrimonio;


    @Column(name = "codigo_re")
    private String codigoRe;

    @Column
    private String status;

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



    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_demanda")
    private Demanda demanda;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_operacao")
    private Operacao operacao;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_centro")
    private CentroCusto centro;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_equipamento")
    private Equipamento equipamento;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_modelo")
    private Modelo modelo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_memoria")
    private Memoria memoria;



}
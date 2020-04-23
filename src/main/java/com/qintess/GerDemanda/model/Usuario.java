package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private String email;

    @Column
    private String cpf;

    @JsonIgnore
    @Column
    private String senha;

    @Column(name = "codigo_re")
    private String codigoRe;

    @Column(name = "codigo_bb")
    private String codigoBB;

    @Column
    private String empresa;

    @Column
    private Integer demanda;

    @Column
    private String Celular;

    @Temporal(TemporalType.DATE)
    private Date nascimento;

    @Column
    private String status;

    @Column(name = "primeiro_acesso")
    private Boolean primeiroAcesso;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_contrato")
    private Contrato contrato;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_cargo")
    private Cargo cargo;

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioSigla", cascade = CascadeType.PERSIST)
    List<UsuarioSigla> listaSiglas;

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioPerfil", cascade = CascadeType.PERSIST)
    List<UsuarioPerfil> listaPerfil;
}
package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "OrdemFornecimento")
@Table(name = "ordem_forn")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdemFornecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "numero_OF")
    private String numeroOF;

    @Column(name = "numero_OF_genti")
    private String numeroOFGenti;

    @Column
    private String fabrica;

    @Column
    private String tema;

    @Column
    private boolean agil;

    @Column(name = "usti_bb")
    private double ustiBB;

    @Column
    private String uor;

    @Column
    private String demanda;

    @Column
    private String acao;

    @Column
    private String referencia;

    @Column
    private String tipo;

    @Column(name = "cd_ti")
    private String cdTI;

    @Column(name = "dt_abertura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAbertura;

    @Column(name = "dt_previsao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtPrevisao;

    @Column(name = "dt_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtEntrega;

    @Column(name = "dt_devolvida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtDevolvida;

    @Column(name = "dt_recusa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtRecusa;

    @Column(name = "dt_aceite")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAceite;

    @Column(name = "responsavel_t")
    private String responsavelTecnico;

    @Column(name = "gerente_t")
    private String gerenteTecnico;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_sigla")
    private Sigla sigla;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_situacao_genti")
    private Situacao situacaoGenti;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_situacao_usu")
    private Situacao situacaoUsu;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "ordemFornecimento", cascade = CascadeType.MERGE)
    List<UsuarioOrdemFornecimento> listaUsuarios;

}


















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
@Table(name = "vagas")
public class Vagas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;


    @Column
    private String qualitor;

    @Column
    private String vale_alimentacao;

    @Column
    private String vale_refeicao;

    @Column
    private String remuneracao;

    @Column
    private String bonus;

    @Column
    private String cesta;

    @Column
    private String flash;


    @Temporal(TemporalType.DATE)
    private Date data_inicio;

    @Temporal(TemporalType.DATE)
    private Date data_final;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_etapa")
    private Etapa etapa;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_cargo")
    private Cargo cargo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_recrutador")
    private Recrutador recrutador;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_bu")
    private Bu bu;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_especialidade")
    private Especialidade especialidade;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_status")
    private Status status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_plano")
    private PlanoSaude planoSaude;


}

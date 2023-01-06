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
@Table(name = "rh")
public class Rh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column
    private String candidato;

    @Column
    private String numero_zoro;

    @Column
    private String cpf;

    @Column
    private String rg;

    @Column
    private String telefone;

    @Column
    private String email;

    @Column
    private String vale_alimentacao;

    @Column
    private String vale_refeicao;

    @Column
    private String remuneracao;

    @Column
    private String bonus;


    @Column
    private String plano_saude;

    @Column
    private String cesta;

    @Column
    private String flash;


    @Temporal(TemporalType.DATE)
    private Date data_inicio;


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
    @JoinColumn(name = "fk_operacao")
    private Operacao operacao;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_especialidade")
    private Especialidade especialidade;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_status")
    private Status status;

}

package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "proposta")
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_candidato")
    private Candidatos candidatos;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_status_candidato")
    private StatusCandidato status_candidatos;

    @Column
    private boolean vale_alimetacao;

    @Column
    private boolean plano_saude;

    @Column
    private boolean plano_odonto;

    @Column
    private boolean seguro_vida;

    @Column
    private boolean vale_refeicao;

    @Column
    private String remuneracao;

    @Column
    private String flash;



}

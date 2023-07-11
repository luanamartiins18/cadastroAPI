package com.qintess.GerDemanda.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "historico_proposta")
@Component
public class HistoricoProposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data_inicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data_final;

    @Column
    private String vigente;

    @ManyToOne
    @JoinColumn(name = "fk_status_candidato")
    private StatusCandidato status_candidatos;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_proposta")
    private Proposta proposta;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_candidato")
    private Candidatos candidatos;

}

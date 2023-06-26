package com.qintess.GerDemanda.model;

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
@Table(name = "historico_candidato")
@Component

public class HistoricoCandidato {
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
    @JoinColumn(name = "fk_candidato")
    private Candidatos candidatos;


    @ManyToOne
    @JoinColumn(name = "fk_status_candidato")
    private StatusCandidato status_candidato;
}

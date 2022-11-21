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
@Table(name = "historico_maquinas")
@Component
public class HistoricoMaquinas {

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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_modelo")
    private Modelo modelo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_equipamento")
    private Equipamento equipamento;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;



}

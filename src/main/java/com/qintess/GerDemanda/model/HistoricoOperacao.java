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
@Table(name = "historico_operacao")
@Component
public class HistoricoOperacao {

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
    @JoinColumn(name = "fk_operacao")
    private Operacao operacao;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

}
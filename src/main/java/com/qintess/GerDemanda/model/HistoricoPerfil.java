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
@Table(name = "historico_perfil")
@Component
public class HistoricoPerfil {

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
    @JoinColumn(name = "fk_perfil")
    private Perfil perfil;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;


    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }
}
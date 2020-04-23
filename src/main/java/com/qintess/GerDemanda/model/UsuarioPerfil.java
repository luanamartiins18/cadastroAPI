package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuario_x_perfil")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_criacao")
    private Date dtCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_exclusao")
    private Date dtExclusao;

    private Integer status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuarioPerfil;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_perfil")
    private Perfil perfil;
}
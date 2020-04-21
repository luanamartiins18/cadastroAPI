package com.qintess.GerDemanda.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "disciplina")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "fk_perfil")
    private Perfil perfil;

}
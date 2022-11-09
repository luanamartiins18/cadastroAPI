package com.qintess.GerDemanda.model;

import lombok.*;

import javax.persistence.*;


@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "modelo")
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    private String descricao;
}

package com.qintess.GerDemanda.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "uni_medida")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UniMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column
    private String descricao;

}
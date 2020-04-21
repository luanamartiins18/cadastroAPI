package com.qintess.GerDemanda.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "valor_ustibb")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ValorUstibb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    private Double valor;
    private Integer ativo;
}
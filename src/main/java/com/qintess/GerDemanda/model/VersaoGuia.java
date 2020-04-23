package com.qintess.GerDemanda.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "versao_guia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VersaoGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    private String descricao;
}
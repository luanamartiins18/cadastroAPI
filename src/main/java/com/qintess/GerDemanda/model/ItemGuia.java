package com.qintess.GerDemanda.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item_guia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    private String componente;
    private Integer limite_itens;
    private Double valor;
    private String descricao_complex;

    @ManyToOne
    @JoinColumn(name = "fk_uni_medida")
    private UniMedida uniMedida;

    @ManyToOne
    @JoinColumn(name = "fk_tarefa_guia")
    private TarefaGuia tarefaGuia;

    @ManyToOne
    @JoinColumn(name = "fk_complex_guia")
    private ComplexGuia complexGuia;

}
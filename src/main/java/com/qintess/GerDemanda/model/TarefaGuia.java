package com.qintess.GerDemanda.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tarefa_guia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TarefaGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    private String plataforma;
    private String atividade;
    private String tarefa;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "fk_disciplina")
    private Disciplina disciplina;

}
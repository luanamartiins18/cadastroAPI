package com.qintess.GerDemanda.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "candidatos")
public class Candidatos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column
    private String candidatos;

    @Column
    private String cpf;

    @Column
    private String rg;

    @Column
    private String email;

    @Column
    private String telefone;

    @Column
    private String remuneracao_atual;

    @Column
    private String vale_alimentacao_atual;

    @Column
    private String vale_refeicao_atual;

    @Column
    private String flash_atual;

    @Column
    private String cesta_atual;

    @Column
    private String bonus_atual;

    @Column
    private String remuneracao_pretensao;

    @Column
    private String vale_alimentacao_pretensao;

    @Column
    private String vale_refeicao_pretensao;

    @Column
    private String observacao;

    @Column
    private String motivo;

    @Column
    private String flash_pretensao;

    @Column
    private String cesta_pretensao;

    @Column
    private String bonus_pretensao;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_status_candidato")
    private StatusCandidato status_candidato;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_vagas")
    private Vagas vagas;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_plano")
    private PlanoSaude planoSaude;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_planoPretensao")
    private PlanoSaudePretensao planoPretensao;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_curriculo")
    private Curriculo curriculo;

}

package com.qintess.GerDemanda.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tarefa_of")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TarefaOf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    private String historia;
    private String sprint;
    private Integer quantidade;
    private String artefato;
    private String observacao;
    private String num_tarefa;
    private String perfil;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_inclusao")
    private Date dtInclusao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_alteracao")
    private Date dtAlteracao;

    @ManyToOne
    @JoinColumn(name = "fk_situacao")
    private Situacao situacao;

    @ManyToOne
    @JoinColumn(name = "fk_item_guia")
    private ItemGuia itemGuia;

    @ManyToOne
    @JoinColumn(name = "fk_of_usuario")
    private UsuarioOrdemFornecimento usuarioOrdemFornecimento;

}
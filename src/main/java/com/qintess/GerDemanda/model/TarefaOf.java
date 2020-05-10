package com.qintess.GerDemanda.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Long quantidade;
    private String artefato;
    private String observacao;
    private String num_tarefa;
    private String perfil;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_inclusao")
    private Date dtInclusao;

    @UpdateTimestamp
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

    public TarefaOf(
            String numero_of,
            String colaborador,
            String status_of,
            Double valor_ustibb,
            String referencia,
            String sigla,
            Long quantidade
    ) {
        this.quantidade = quantidade;
        this.usuarioOrdemFornecimento = UsuarioOrdemFornecimento
                .builder()
                .usuario(Usuario.builder().nome(colaborador).build())
                .ordemFornecimento(OrdemFornecimento
                        .builder()
                        .numeroOFGenti(numero_of)
                        .referencia(referencia)
                        .sigla(Sigla
                                .builder()
                                .descricao(sigla)
                                .build()
                        )
                        .situacaoUsu(Situacao
                                .builder()
                                .descricao(status_of)
                                .build()
                        )
                        .build())
                .build();

        this.itemGuia = ItemGuia
                .builder()
                .valor(valor_ustibb)
                .build();
    }

    public TarefaOf(
            String numero_of,
            String colaborador,
            String status_of,
            Double valor_ustibb,
            String referencia,
            String sigla
    ) {
        this.quantidade = quantidade;
        this.usuarioOrdemFornecimento = UsuarioOrdemFornecimento
                .builder()
                .usuario(Usuario.builder().nome(colaborador).build())
                .ordemFornecimento(OrdemFornecimento
                        .builder()
                        .numeroOFGenti(numero_of)
                        .referencia(referencia)
                        .sigla(Sigla
                                .builder()
                                .descricao(sigla)
                                .build()
                        )
                        .situacaoUsu(Situacao
                                .builder()
                                .descricao(status_of)
                                .build()
                        )
                        .build())
                .build();

        this.itemGuia = ItemGuia
                .builder()
                .valor(valor_ustibb)
                .build();
    }

}
package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String corpo;

    private Integer status;

    private String titulo;

    @Column(name = "tp_mensagem")
    private String tipoMensagem;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_responsavel")
    private Usuario responsavel;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_criacao")
    private Date dtCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_expiracao")
    private Date dtExpiracao;

    @JsonIgnore
    @OneToMany(mappedBy = "mensagem", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UsuarioMensagem> listaUsuarios;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_sigla")
    private Sigla sigla;
}
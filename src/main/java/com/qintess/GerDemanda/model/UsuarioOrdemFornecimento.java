package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuario_x_of")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioOrdemFornecimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_exclusao")
	private Date dtExclusao;

	private Integer status;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_ordem_forn")
	private OrdemFornecimento ordemFornecimento;
}

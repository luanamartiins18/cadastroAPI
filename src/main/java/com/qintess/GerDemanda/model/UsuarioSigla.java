package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "usuario_x_sigla")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioSigla {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_exclusao")
	private Date dtExclusao;
	
	private int status;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuarioSigla;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_sigla")
	private Sigla sigla;
}















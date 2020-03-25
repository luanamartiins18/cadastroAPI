package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "usuario_x_perfil")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioPerfil{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_criacao")
	private Calendar dtCriacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_exclusao")
	private Calendar dtExclusao;
	
	private int status;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuarioPerfil;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_perfil")
	private Perfil perfil;
}















package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;

	@Column
	private String nome;

	@Column
	private String email;

	@Column
	private String cpf;

	@JsonIgnore
	@Column
	private String senha;

	@Column(name="codigo_re")
	private String codigoRe;

	@Column(name="codigo_bb")
	private String codigoBB;

	@Column
	private String empresa;

	@Column
	private int demanda;

	@Column
	private String Celular;

	@Temporal(TemporalType.TIMESTAMP)
	private Date nascimento;

	@Column
	private String status;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="fk_contrato")
	private Contrato contrato;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="fk_cargo")
	private Cargo cargo;

	@JsonIgnore
	@OneToMany(mappedBy = "usuarioSigla", fetch = FetchType.LAZY)
	Set<UsuarioSigla> listaSiglas = new HashSet<UsuarioSigla>();

	@JsonIgnore
	@OneToMany(mappedBy = "usuarioPerfil", fetch = FetchType.LAZY)
	Set<UsuarioPerfil> listaPerfil = new HashSet<UsuarioPerfil>();
}



























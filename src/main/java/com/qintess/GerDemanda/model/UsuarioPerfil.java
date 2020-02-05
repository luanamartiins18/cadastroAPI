package com.qintess.GerDemanda.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "UsuarioPerfil")
@Table(name = "usuario_x_perfil")
public class UsuarioPerfil {
	
	@Id
	private int id;
	
	@Column(name = "dt_criacao")
	@Temporal(TemporalType.DATE)
	private Calendar dtCriacao;
	
	@Column(name = "dt_exclusao")
	@Temporal(TemporalType.DATE)
	private Calendar dtExclusao;
	
	@Column
	private int status;
	
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "fk_perfil")
	private Perfil perfil;	
	
	public int getId() {
		return id;
	}

	public Calendar getDtCriacao() {
		return dtCriacao;
	}

	public Calendar getDtExclusao() {
		return dtExclusao;
	}

	public int getStatus() {
		return status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

}






















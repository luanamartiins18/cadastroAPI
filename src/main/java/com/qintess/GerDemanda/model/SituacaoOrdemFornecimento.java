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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="situacao_x_of")
public class SituacaoOrdemFornecimento {
	
	@Id
	private int id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_criacao")
	Calendar dtCriacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_exclusao")
	Calendar dtExclusao;
	
	private int status;
	
	private String tipo;
	
	@ManyToOne
	@JoinColumn(name = "fk_situacao")	
	private Situacao situacao;
	
	@ManyToOne
	@JoinColumn(name = "fk_of")	
	private OrdemFornecimento ordemFornecimento;			
	
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

	public String getTipo() {
		return tipo;
	}

	public Situacao getSituacao() {
		return situacao;
	}
}

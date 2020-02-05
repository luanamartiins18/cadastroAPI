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

@Entity(name = "HistoricoOrdemFornecimento")
@Table(name = "historico_OF")

public class HistoricoOrdemFornecimento {
	
	@Id
	@Column
	private int id;
	
	@Column(name = "dt_criacao")
	@Temporal(TemporalType.DATE)
	private Calendar dtCriacao;
	
	@ManyToOne
	@JoinColumn(name = "fk_ordem_forn")
	private OrdemFornecimento ordemFornecimento;
	
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "fk_situacao")
	private Situacao situacao;
}
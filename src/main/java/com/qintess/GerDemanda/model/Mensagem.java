package com.qintess.GerDemanda.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Mensagem {
	@Id
	private int id;
	
	private String corpo;
	
	@Column(name="tp_mensagem")
	private String tipoMensagem;
	
	private int status;
	
	@ManyToOne
	@JoinColumn(name="fk_responsavel")
	private Usuario responsavel;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_criacao")
	private Calendar dtCriacao;

	@Temporal(TemporalType.DATE)
	@Column(name="dt_expiracao")
	private Calendar dtExpiracao;
	
	@OneToMany(mappedBy = "mensagem", fetch = FetchType.EAGER)
	private List<UsuarioMensagem> listaUsuarios;
	
	
	public List<UsuarioMensagem> getListaUsuarios() {
		return listaUsuarios;
	}	

	public int getId() {
		return id;
	}

	public String getCorpo() {
		return corpo;
	}

	public String getTipoMensagem() {
		return tipoMensagem;
	}

	public int getStatus() {
		return status;
	}

	public Usuario getResponsavel() {
		return responsavel;
	}

	public Calendar getDtCriacao() {
		return dtCriacao;
	}

	public Calendar getDtExpiracao() {
		return dtExpiracao;
	}
}
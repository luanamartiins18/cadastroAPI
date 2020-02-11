package com.qintess.GerDemanda.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "usuario_x_mensagem")
public class UsuarioMensagem {
	
	@Id
	private int id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_leitura")
	private Calendar dtLeitura;
	
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuarioMens;
	
	@ManyToOne
	@JoinColumn(name = "fk_mensagem")
	private Mensagem mensagem;

	public int getId() {
		return id;
	}

	public Calendar getDtLeitura() {
		return dtLeitura;
	}

	public Usuario getUsuarioMens() {
		return usuarioMens;
	}
}


























































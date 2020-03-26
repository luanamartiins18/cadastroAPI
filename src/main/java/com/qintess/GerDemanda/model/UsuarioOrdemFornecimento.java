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

@Entity
@Table(name = "usuario_x_of")
public class UsuarioOrdemFornecimento {
	
	@Id
	private int id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_criacao")
	private Calendar dtCriacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_exclusao")
	private Calendar dtExclusao;
	
	private int status;
	
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "fk_ordem_forn")
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

	public Usuario getUsuario() {
		return usuario;
	}
}

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

@Entity(name = "OrdemFornecimento")
@Table(name = "ordem_forn")
public class OrdemFornecimento {
	
	@Id
	private int id;
	
	@Column(name = "numero_OF")
	private String numeroOF;
	
	@Column(name = "numero_OF_genti")
	private String numeroOFGenti;
	
	@Column
	private String fabrica;
	
	@Column
	private String tema;
	
	@Column
	private boolean agil;
	
	@Column(name = "usti_bb")
	private double ustiBB;
	
	@Column
	private String uor;
	
	@Column
	private String demanda;
	
	@Column
	private String acao;
	
	@Column
	private String tipo;
	
	@Column(name = "cd_ti")
	private String cdTI;
	
	@Column(name = "dt_abertura")
	@Temporal(TemporalType.DATE)
	private Calendar dtAbertura;
	
	@Column(name = "dt_previsao")
	@Temporal(TemporalType.DATE)
	private Calendar dtPrevisao;
	
	@Column(name = "dt_entrega")
	@Temporal(TemporalType.DATE)
	private Calendar dtEntrega;
	
	@Column(name = "dt_devolvida")
	@Temporal(TemporalType.DATE)
	private Calendar dtDevolvida;
	
	@Column(name = "dt_recusa")
	@Temporal(TemporalType.DATE)
	private Calendar dtRecusa;
	
	@Column(name = "dt_aceite")
	@Temporal(TemporalType.DATE)
	private Calendar dtAceite;
	
	@ManyToOne  
	@JoinColumn(name = "fk_situacao")
	private Situacao situacao;

	@ManyToOne  
	@JoinColumn(name = "fk_situacao_alm")
	private Situacao situacaoAlm;
	
	@ManyToOne  
	@JoinColumn(name = "fk_sigla")
	private Sigla sigla;
	
	@ManyToOne  
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;
	
	@Column(name = "responsavel_t")
	private String responsavelTecnico;
	
	@Column(name = "gerente_t")
	private String gerenteTecnico;

	public int getId() {
		return id;
	}

	public String getNumeroOF() {
		return numeroOF;
	}

	public String getNumeroOFGenti() {
		return numeroOFGenti;
	}

	public String getFabrica() {
		return fabrica;
	}

	public String getTema() {
		return tema;
	}

	public boolean isAgil() {
		return agil;
	}

	public double getUstiBB() {
		return ustiBB;
	}

	public String getUor() {
		return uor;
	}

	public String getDemanda() {
		return demanda;
	}

	public String getAcao() {
		return acao;
	}

	public String getTipo() {
		return tipo;
	}

	public String getCdTI() {
		return cdTI;
	}

	public Calendar getDtAbertura() {
		return dtAbertura;
	}

	public Calendar getDtPrevisao() {
		return dtPrevisao;
	}

	public Calendar getDtEntrega() {
		return dtEntrega;
	}

	public Calendar getDtDevolvida() {
		return dtDevolvida;
	}

	public Calendar getDtRecusa() {
		return dtRecusa;
	}

	public Calendar getDtAceite() {
		return dtAceite;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public Situacao getSituacaoAlm() {
		return situacaoAlm;
	}

	public Sigla getSigla() {
		return sigla;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String getResponsavelTecnico() {
		return responsavelTecnico;
	}

	public String getGerenteTecnico() {
		return gerenteTecnico;
	}
}





























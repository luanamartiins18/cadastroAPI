package com.qintess.GerDemanda.model;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.qintess.GerDemanda.model.Acesso;
import com.qintess.GerDemanda.model.Contrato;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private String nome;
	
	@Column
	private String email;
	
	@Column
	private String cpf;
	
	@Column
	private String senha;
	
	
	@Column(name="codigo_re")
	private String codigoRe;
	
	@Column(name="codigo_bb")
	private String codigoBB;
	
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getCpf() {
		return cpf;
	}

	public String getSenha() {
		return senha;
	}

	public String getCodigoRe() {
		return codigoRe;
	}

	public String getCodigoBB() {
		return codigoBB;
	}

	public String getEmpresa() {
		return empresa;
	}

	public int getDemanda() {
		return demanda;
	}

	public String getCelular() {
		return Celular;
	}

	public Calendar getNascimento() {
		return nascimento;
	}

	public String getStatus() {
		return status;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public Acesso getAcesso() {
		return acesso;
	}

	@Column
	private String empresa;
	
	@Column
	private int demanda;
	
	@Column
	private String Celular;
	
	@Temporal(TemporalType.DATE)
	private Calendar nascimento;
	
	@Column
	private String status;
	
	@ManyToOne
	@JoinColumn(name="fk_contrato")
	private Contrato contrato;
	
	@ManyToOne
	@JoinColumn(name="fk_acesso")
	private Acesso acesso;
}
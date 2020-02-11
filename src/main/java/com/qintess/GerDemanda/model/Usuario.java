package com.qintess.GerDemanda.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.qintess.GerDemanda.model.Cargo;
import com.qintess.GerDemanda.model.Contrato;

@Entity
public class Usuario {
		
	@Id
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
	@JoinColumn(name="fk_cargo")
	private Cargo cargo;	
	
	@OneToMany(mappedBy = "usuario")
	List<UsuarioOrdemFornecimento> listaOfs;	
	
	@OneToMany(mappedBy = "usuarioSigla", fetch = FetchType.EAGER)
	Set<UsuarioSigla> listaSiglas = new HashSet<UsuarioSigla>();
	
	@OneToMany(mappedBy = "usuarioPerfil", fetch = FetchType.EAGER)
	Set<UsuarioPerfil> listaPerfil= new HashSet<UsuarioPerfil>();	
	
	public Set<UsuarioPerfil> getListaPerfil(){
		return listaPerfil;
	}
	
	public Set<UsuarioSigla> getListaSiglas(){
		return listaSiglas;
	}	
	
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

	public Cargo getCargo() {
		return cargo;
	}	
}



























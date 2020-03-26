package com.qintess.GerDemanda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sigla")
public class Sigla {
	
	@Id
	private int id;
	
	@Column
	private String descricao;

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}	 	
}
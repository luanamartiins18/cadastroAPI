package com.qintess.GerDemanda.model;

import javax.persistence.*;

@Entity
@Table(name = "cargo")
public class Cargo {
	
	@Id
	@GeneratedValue
	private int id;
	
	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	@Column
	private String descricao;
}
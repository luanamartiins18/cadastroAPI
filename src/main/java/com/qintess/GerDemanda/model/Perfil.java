package com.qintess.GerDemanda.model;

import javax.persistence.*;

@Entity
@Table(name = "perfil")
public class Perfil {
	
	@Id
	@GeneratedValue
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
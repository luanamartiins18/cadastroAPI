package com.qintess.GerDemanda.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Acesso {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private String descricao;
	
	@OneToMany(mappedBy = "id")
	private List<Usuario> listaUsuarios;
}
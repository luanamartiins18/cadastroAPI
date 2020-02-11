package com.qintess.GerDemanda.model;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Situacao {
	
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
	
	@OneToMany(mappedBy = "situacao")
	List<SituacaoOrdemFornecimento> listaOfs;
}
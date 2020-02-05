package com.qintess.GerDemanda.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.qintess.GerDemanda.model.OrdemFornecimento;

public class OrdemFornecimentoService {

	public List<OrdemFornecimento> getOrdemDeFornecimento() {
		
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();					
		
		// Pega todas as OFs que estão em execução
		TypedQuery<OrdemFornecimento> query = 
				em.createQuery("select ofrn from "
								+ " OrdemFornecimento ofrn, Situacao s "
								+ "where ofrn.situacao.id = s.id and s.id = 6 ", OrdemFornecimento.class);						
		
		List<OrdemFornecimento> of = query.getResultList();				
		
		return of;
	}
	
}

package com.qintess.GerDemanda.service;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.qintess.GerDemanda.model.Situacao;

public class SituacaoService {

	
	public List<Situacao> getSituacao() {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager(); 
		
		Query query = em.createQuery("from Situacao sit order by sit.descricao");	
		
		List<Situacao> listaSit = query.getResultList();		
		
		em.close();
		entityManagerFactory.close();		
		return listaSit;	
	}
}

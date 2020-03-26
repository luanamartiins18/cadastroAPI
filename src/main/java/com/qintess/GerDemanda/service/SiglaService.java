package com.qintess.GerDemanda.service;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.qintess.GerDemanda.PersistenceHelper;
import com.qintess.GerDemanda.model.Sigla;

public class SiglaService {

	
	public List<Sigla> getSiglas() {
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager(); 
		
		Query query = em.createQuery("from sigla sig order by sig.descricao");
		
		List<Sigla> listaSit = query.getResultList();		
		
		em.close();
		return listaSit;	
	}
}

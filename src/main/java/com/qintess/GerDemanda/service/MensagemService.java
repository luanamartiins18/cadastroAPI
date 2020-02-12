package com.qintess.GerDemanda.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import com.qintess.GerDemanda.model.Mensagem;

public class MensagemService {
	
	public List<Mensagem> getMensagens() {
		
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();					
		
		TypedQuery<Mensagem> query = em.createQuery("from Mensagem", Mensagem.class);
		
		List<Mensagem> listaM = query.getResultList();		

		em.close();
		entityManagerFactory.close();
		return listaM;		
	}
		
}

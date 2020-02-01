package com.qintess.GerDemanda.service;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.qintess.GerDemanda.model.Usuario;

public class UsuarioService {
	
	public Usuario getUsuarioById(int id){
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		Usuario usuario = em.find(Usuario.class, id);
		
		
		em.close();
		entityManagerFactory.close();		
		return usuario;
	}
	
	
	
	
	

}

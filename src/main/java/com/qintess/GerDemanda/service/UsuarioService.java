package com.qintess.GerDemanda.service;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.qintess.GerDemanda.model.Perfil;
import com.qintess.GerDemanda.model.Sigla;
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
	
	public Usuario getUsuarioByRe(String re) {
		
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();					
		
		TypedQuery<Usuario> query = 
				em.createQuery("from Usuario u "
					+ " where u.codigoRe = :re", Usuario.class);						
		
		query.setParameter("re", re);
		List<Usuario> usuarioList = query.getResultList();			
		
		if(usuarioList.size() == 0) {
			return null;
		}				
		Usuario usuario = usuarioList.get(0);
		
		TypedQuery<Perfil> queryP = 
				em.createQuery("select p from Usuario u, UsuarioPerfil up, Perfil p "
					+ " where u.id = up.usuario.id and up.perfil.id = p.id "				
					+ " and u.codigoRe = :re and up.status = 1", Perfil.class);
		
		queryP.setParameter("re", re);		
		List<Perfil> listaPerfil = queryP.getResultList();
		
		if(listaPerfil.size() != 0) {
			usuario.setListaPerfil(listaPerfil);
		}			
		
		TypedQuery<Sigla> queryS = 
				em.createQuery("select s from Usuario u, Sigla s"
					+ " where u.sigla = s.id"				
					+ " and u.codigoRe = :re", Sigla.class);
		
		queryS.setParameter("re", re);		
		List<Sigla> listaSigla = queryS.getResultList();
		
		if(listaSigla.size() != 0) {
			usuario.setSigla(listaSigla.get(0));
		}	
		
		em.close();
		entityManagerFactory.close();
				
		return usuario;
	}
	
	public boolean checkUsuario(String re, String senha) {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();		
		
		boolean resultado = true;		
		Query query = em.createQuery("from Usuario where codigo_re = :re and senha = :senha");
		query.setParameter("re", re);
		query.setParameter("senha", senha);
		
		List<Usuario> usuario = query.getResultList();
		
		if(usuario.size() == 0) {
			resultado = false;
		}
		
		em.close();
		entityManagerFactory.close();
		return resultado;
	}
}















package com.qintess.GerDemanda.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.json.JSONArray;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.model.Perfil;
import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.model.Usuario;

public class UsuarioService {

	
	public HashMap<String, Object> getPerfilUsuario(int idUsu){
		
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		String sql = "select p.descricao from usuario_x_perfil up " + 
						"inner join perfil p " + 
						"	on p.id = up.fk_perfil " + 
						"where up.fk_usuario = :idUsu and up.status = 1";
		
		Query query = em.createNativeQuery(sql);
		query.setParameter("idUsu", idUsu);		
		
		List<Object> res = query.getResultList();
		HashMap<String, Object> perfil = new HashMap<String, Object>();		
			
		perfil.put("descricao", res.get(0));
		
		em.close();
		entityManagerFactory.close();	
		return perfil;
	}
	
	
	public List<Usuario> getUsuariosAtivos(){
		
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();					
		
		TypedQuery<Usuario> query = 
				em.createQuery("select distinct usu from "
								+ " Usuario usu "
								+ "left join fetch usu.listaSiglas lis "
								+ "left join fetch usu.listaPerfil lp "
								+ "where usu.status = 'Ativo' and usu.cargo.id = 3", Usuario.class);		
		
		List<Usuario> listaUsu = query.getResultList();					
		
		em.close();
		entityManagerFactory.close();
		return listaUsu;
	}		
	
	public List<Usuario> getUsuarioBySigla(int id){
		
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();					
		
		TypedQuery<Usuario> query = 
				em.createQuery("select distinct usu from "
								+ " Usuario usu "
								+ "inner join fetch usu.listaSiglas lis "
								+ "inner join fetch usu.listaPerfil lp "
								+ "where lis.sigla.id = :id "
								+ "and usu.status = 'ativo' and usu.cargo.id = 3"
								+ "order by usu.nome ", Usuario.class);			
		query.setParameter("id", id);
		
		List<Usuario> listaUsu = query.getResultList();				
		
		
		em.close();
		entityManagerFactory.close();
		return listaUsu;
	}
	
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
		
		String sql = "FROM Usuario usu "
						+ " left join fetch usu.listaSiglas sgl"
						+ " left join fetch sgl.sigla "
						+ " left join fetch usu.listaPerfil lp "
						+ " left join fetch lp.perfil "				 		
				 		+ "where usu.codigoRe = :re ";
		
		
		TypedQuery<Usuario>  query = em.createQuery(sql, Usuario.class);
		query.setParameter("re", re);		
		List<Usuario> usuario = query.getResultList();
		
		em.close();
		entityManagerFactory.close();
		return usuario.get(0);
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















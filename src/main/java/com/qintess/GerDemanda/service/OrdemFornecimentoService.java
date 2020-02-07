package com.qintess.GerDemanda.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
								+ "where ofrn.situacao.id = s.id and s.id = 6 "
								+ "order by ofrn.sigla.descricao, ofrn.responsavelTecnico", OrdemFornecimento.class);						
		
		List<OrdemFornecimento> of = query.getResultList();				
		
		
		em.close();
		entityManagerFactory.close();
		return of;
	}
	
	public void registraUsuSit(String usu, String sit, int id){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();		
		
		Query query = 
				em.createQuery("update OrdemFornecimento ofrn "
							 	+ "set ofrn.usuario.id = :usu,"
							 	+ "ofrn.situacaoAlm.id = :sit "
							 	+ "where ofrn.id = :id");		
		
		query.setParameter("usu", Integer.parseInt(usu));
		query.setParameter("sit", Integer.parseInt(sit));
		query.setParameter("id",  id);
		
		query.executeUpdate();	
		
		em.close();
		entityManagerFactory.close();		
	}
	
	
}

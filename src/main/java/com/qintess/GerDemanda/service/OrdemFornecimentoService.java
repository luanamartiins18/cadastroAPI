package com.qintess.GerDemanda.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.model.Situacao;

public class OrdemFornecimentoService {

	public List<OrdemFornecimento> getOrdemDeFornecimento() {
		
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();					
		
		String sql = "select distinct orf from OrdemFornecimento orf "
					+ " join fetch orf.listaSituacoes"
					+ " left join fetch orf.listaUsuarios";
		
		
		
		TypedQuery<OrdemFornecimento> query = em.createQuery(sql, OrdemFornecimento.class);
		List<OrdemFornecimento> ordemF = query.getResultList();		
		
		em.close();
		entityManagerFactory.close();
		
		return ordemF;
	}
	
	public void registraUsuSit(String usu, String sit, int id){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();		
		
		//TODO: Criar transação para poder executar o update
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

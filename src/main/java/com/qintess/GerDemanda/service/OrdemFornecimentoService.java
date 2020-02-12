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
		
		String sql = "select distinct orf from OrdemFornecimento orf "
					+ " left join fetch orf.listaUsuarios "
					+ " inner join fetch orf.sigla "
					+ " inner join fetch orf.situacaoGenti "
					+ " left join fetch orf.situacao "
					+ " where orf.situacaoGenti.id = 6";
		
		
		
		TypedQuery<OrdemFornecimento> query = em.createQuery(sql, OrdemFornecimento.class);
		List<OrdemFornecimento> ordemF = query.getResultList();		
		
		em.close();
		entityManagerFactory.close();
		
		return ordemF;
	}
	
	public void registraUsuSit(List<Integer> listaUsu, int situacao, int ofId){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();			
		em.getTransaction().begin();
		
		//TODO alterar o campo fk_situacao_alm para fk_situacao apos alterar no banco
		
		String sql = "update ordem_forn set fk_situacao_alm = :sit where id = :ofId";
		Query query = em.createNativeQuery(sql);
		query.setParameter("sit", situacao);
		query.setParameter("ofId", ofId);
		query.executeUpdate();
		/*Atualização da situação da OF*/
		
		sql = "select fk_usuario from usuario_x_of where status = 1 and fk_ordem_forn = " + Integer.toString(ofId);
		query = em.createNativeQuery(sql);
		List<Integer> usuAtual = query.getResultList();			
		String strListaUsu = "";
		
		for(int usu: listaUsu) {	
			
			strListaUsu += (strListaUsu.equals("")) ? Integer.toString(usu) : "," + Integer.toString(usu); 
			
			/*Se um usuário marcado no checkbox não estiver relacionado a OF, relaciona*/
			if(!usuAtual.contains(usu)) {
				sql = "INSERT INTO usuario_x_of (dt_criacao, fk_ordem_forn, fk_usuario, dt_exclusao, status) VALUES("
						+ "curdate(), " + Integer.toString(ofId) + " , " + Integer.toString(usu) + " , null, 1);";
			
				query = em.createNativeQuery(sql);	
				query.executeUpdate();
			}		
			
		}
		
		sql = "update usuario_x_of set status = 0, dt_exclusao = curdate() "
				+ " where fk_ordem_forn = " + Integer.toString(ofId) + " and status = 1 and "
				+ " fk_usuario not in (" + strListaUsu + ");";		
		
		query = em.createNativeQuery(sql);
		query.executeUpdate();	
			
		em.getTransaction().commit();		
		em.close();
		entityManagerFactory.close();		
	}	
}

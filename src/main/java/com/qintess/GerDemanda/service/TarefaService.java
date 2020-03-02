package com.qintess.GerDemanda.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TarefaService {
	
	
	
	public List<HashMap<String, Object>> getItensGuia(){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		String sql = "select concat(concat(tarefa, ' - '), tg.descricao) as item, d.id disciplina, tg.id from tarefa_guia tg " + 
						"inner join disciplina d " + 
						"on tg.fk_disciplina = d.id ";
		
		Query query = em.createNativeQuery(sql);
		
		
		List<Object> res = query.getResultList();
		List<HashMap<String, Object>> itens = new ArrayList<HashMap<String, Object>>();		
		
		for(Object obj: res) {
			HashMap<String, Object> atual = new HashMap<String, Object>();
			JSONArray json = new JSONArray(obj);
			
			atual.put("item", json.get(0));
			atual.put("disciplina", json.get(1));
			atual.put("id", json.get(2));
			
			itens.add(atual);			
		}
		
		
		em.close();
		entityManagerFactory.close();
		return itens;
	}
	
	public List<HashMap<String, Object>> getDisciplinas(){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		String sql = "select d.id, d.descricao, p.descricao as p_descricao from disciplina d " + 
						"inner join perfil p " + 
							"on d.fk_perfil = p.id ";
						
		Query query = em.createNativeQuery(sql);		
		
		List<Object> res = query.getResultList();
		List<HashMap<String, Object>> disciplinas = new ArrayList<HashMap<String, Object>>();		
		
		for(Object obj: res) {
			HashMap<String, Object> atual = new HashMap<String, Object>();
			JSONArray json = new JSONArray(obj);
			
			atual.put("id", json.get(0));
			atual.put("descricao", json.get(1));
			atual.put("perfil", json.get(2));
			
			disciplinas.add(atual);			
		}
		
		
		em.close();
		entityManagerFactory.close();
		return disciplinas;				
	}
	
	public String getNumOf(int id) {
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		String sql = "select numero_of_genti from ordem_forn where id = :id";
		Query query = em.createNativeQuery(sql);
		query.setParameter("id", id);
		
		JSONArray json = new JSONArray(query.getResultList());
		
		em.close();
		entityManagerFactory.close();
		return json.getString(0);
	}
	
	
	
	public boolean insereTarefa(String param) {
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		JSONObject json = new JSONObject(param);
		
		String historia = json.getString("historia");
		String sprint = json.getString("sprint");
		String observacao = json.getString("observacao");
		String artefato = json.getString("artefato");	
		int item = json.getInt("item");
		int quantidade = json.getInt("quantidade");
		int idUsu = json.getInt("usu");
		int idOf = json.getInt("of");
		int numTarefa = json.getInt("numTarefa");				
		int idItem = getIdItemGuia(quantidade, item);
		int auxPerfil = json.getInt("perfil");
		String perfil = (auxPerfil == 1) ? "Baixa" : "Alta";		
				
		int usuOf = getIdUsuOf(idUsu, idOf);
		
		String sql = "insert into tarefa_of(historia, sprint, dt_inclusao, dt_alteracao, num_tarefa, perfil, quantidade, artefato, observacao, fk_situacao, fk_item_guia, fk_of_usuario) " + 
				"values (:historia , :sprint , current_timestamp(), null, :numTarefa, :perfil, :quantidade , :artefato , :observacao , 6, :idItem, :usuOf );"; 
		
		Query query = em.createNativeQuery(sql);
		
		query.setParameter("historia", historia);
		query.setParameter("sprint", sprint);
		query.setParameter("quantidade", quantidade);
		query.setParameter("artefato", artefato);
		query.setParameter("observacao", observacao);
		query.setParameter("idItem", idItem);
		query.setParameter("usuOf", usuOf);
		query.setParameter("numTarefa", numTarefa);
		query.setParameter("perfil", perfil);
		
		em.getTransaction().begin();
		query.executeUpdate();	
		em.getTransaction().commit();
		
		em.close();
		entityManagerFactory.close();		
		return true;
	}
	
	
	public HashMap<String, Integer> getValorTarefa(int idUsu, int idOf) {
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
	
		String sql = "select ig.valor, t.fk_situacao from usuario_x_of uof " + 
					"	inner join tarefa_of t " + 
					"		on t.fk_of_usuario = uof.id " + 
					"	inner join item_guia ig " + 
					"		on t.fk_item_guia = ig.id " +  
					"where uof.fk_usuario = :idUsu and uof.fk_ordem_forn = :idOf and status = 1";
		
		int valorPlanejado = 0;
		int valorExecutado = 0;
		
		Query query = em.createNativeQuery(sql);
		query.setParameter("idUsu", idUsu);
		query.setParameter("idOf", idOf);
		
		List<Object> listaValorSit = query.getResultList();
		
		for(Object obj: listaValorSit) {
			JSONArray json = new JSONArray(obj);		
			int valor = json.getInt(0);
			int situ  = json.getInt(1);				
			
			if(situ == 4 || situ == 8) {
				valorExecutado += valor;
			}
			
			if(situ != 2 && situ != 5) {
				valorPlanejado += valor;
			}			
		}
		
		HashMap<String, Integer> resultado = new HashMap<String, Integer>();		
		
		resultado.put("valorExecutado", valorExecutado);
		resultado.put("valorPlanejado", valorPlanejado);

		
		sql = "select ig.valor, t.fk_situacao from usuario_x_of uof " + 
				"	inner join tarefa_of t " + 
				"		on t.fk_of_usuario = uof.id " + 
				"	inner join item_guia ig " + 
				"		on t.fk_item_guia = ig.id " +  
				"where uof.fk_ordem_forn = :idOf and status = 1";
	
		valorPlanejado = 0;
		valorExecutado = 0;
		
		query = em.createNativeQuery(sql);		
		query.setParameter("idOf", idOf);
		
		listaValorSit = query.getResultList();
		
		
		for(Object obj: listaValorSit) {
			JSONArray json = new JSONArray(obj);		
			int valor = json.getInt(0);
			int situ  = json.getInt(1);				
			
			if(situ == 4 || situ == 8) {
				valorExecutado += valor;
			}
			
			if(situ != 2 && situ != 5) {
				valorPlanejado += valor;
			}			
		}
		
	
		
		resultado.put("valorExecutadoTotal", valorExecutado);
		resultado.put("valorPlanejadoTotal", valorPlanejado);	
		
		em.close();
		entityManagerFactory.close();
		return resultado;
	}
	
	
	
	public void atualizaTarefa(String param) {
		
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		JSONObject json = new JSONObject(param);
		
		String historia = json.getString("historia");
		String sprint = json.getString("sprint");
		String observacao = json.getString("observacao");
		String artefato = json.getString("artefato");	
		int item = json.getInt("item");
		int quantidade = json.getInt("quantidade");
		int numTarefa = json.getInt("numTarefa");				
		int idItem = getIdItemGuia(quantidade, item);
		int auxPerfil = json.getInt("perfil");
		String perfil = (auxPerfil == 1) ? "Baixa" : "Alta";	
		int idTrfOf = json.getInt("idTrfOf");
				
		
		
		String sql = "update tarefa_of set historia = :historia, sprint = :sprint, dt_alteracao = current_timestamp(), "
				+ "num_tarefa = :numTarefa, perfil = :perfil, quantidade = :quantidade, artefato = :artefato, "
				+ "observacao = :observacao, fk_item_guia = :idItem "
				+ "where id = :idTrfOf";
				 
		
		Query query = em.createNativeQuery(sql);
		
		query.setParameter("historia", historia);
		query.setParameter("sprint", sprint);
		query.setParameter("quantidade", quantidade);
		query.setParameter("artefato", artefato);
		query.setParameter("observacao", observacao);
		query.setParameter("idItem", idItem);
		query.setParameter("numTarefa", numTarefa);
		query.setParameter("perfil", perfil);
		query.setParameter("idTrfOf", idTrfOf);
		
		em.getTransaction().begin();	
		query.executeUpdate();			
		em.getTransaction().commit();
		
		em.close();
		entityManagerFactory.close();		
	}
	
	
	
	
	private int getIdItemGuia(int quantidade, int idTarefa){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		String sql = "select limite_itens, id from item_guia where fk_tarefa_guia = :idTarefa order by limite_itens asc";
		
		Query query = em.createNativeQuery(sql);
		query.setParameter("idTarefa", idTarefa);
		List<Object> listaItens = query.getResultList();
		
		int id = -1;
		for(Object obj: listaItens) {
			
			JSONArray json = new JSONArray(obj);
			
			if(quantidade <= json.getInt(0)) {
				id = json.getInt(1);
				break;
			}
			
		}
		
		if(id == -1) {
			JSONArray json = new JSONArray(listaItens.get(listaItens.size() - 1));
			id = json.getInt(1);
		}
		
		
		
		
		
		em.close();
		entityManagerFactory.close();
		
		return id;
	}
	
	private int getIdUsuOf(int usu, int of) {
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		String sql = "select id from usuario_x_of where fk_usuario = :usu and fK_ordem_forn = :of and status = 1";
		Query query = em.createNativeQuery(sql);
		
		query.setParameter("usu", usu);
		query.setParameter("of", of);
		
		List<Object> usuOf = query.getResultList();
		JSONArray json = new JSONArray(usuOf);	
		
		em.close();
		entityManagerFactory.close();
		
		return json.getInt(0);		
	}
	
	
	public List<HashMap<String, Object>> getTarefasUsuario(int idUsu, int idOf){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		String sql = "select t.* , s.descricao, ig.valor, cg.descricao as complexidade, tg.fk_disciplina, tg.id as idTrf from tarefa_of t " + 
				"	inner join usuario_x_of u " + 
				"		on t.fk_of_usuario = u.id " + 
				"	inner join situacao s " + 
				"		on s.id = t.fk_situacao " +
				"	inner join item_guia ig " + 
				"		on ig.id = t.fk_item_guia " +
				"	inner join complex_guia cg " + 
				"		on cg.id = ig.fk_complex_guia " +
				"	inner join tarefa_guia tg " + 
				"		on tg.id = ig.fk_tarefa_guia " +
				"	where u.fk_ordem_forn = :idOf and u.fk_usuario = :idUsu";
						
		Query query = em.createNativeQuery(sql);	
		query.setParameter("idUsu", idUsu);
		query.setParameter("idOf", idOf);
		
		List<Object> res = query.getResultList();
		List<HashMap<String, Object>> tarefasUsu = new ArrayList<HashMap<String, Object>>();		
		
		for(Object obj: res) {
			HashMap<String, Object> atual = new HashMap<String, Object>();
			JSONArray json = new JSONArray(obj);
			
			atual.put("id", json.get(0));
			atual.put("historia", json.get(1));
			atual.put("sprint", json.get(2));
			atual.put("dtInclusao", json.get(3));
			atual.put("dtAlteracao", json.get(4));
			atual.put("quantidade", json.get(5));
			atual.put("artefato", json.get(6));
			atual.put("observacao", json.get(7));
			atual.put("numTarefa", json.get(8));
			atual.put("perfil", json.get(9));
			atual.put("fk_situacao", json.get(10));
			atual.put("fk_item_guia", json.get(11));
			atual.put("fk_of_usuario", json.get(12));			
			atual.put("situacao", json.get(13));
			atual.put("valor", json.get(14));
			atual.put("complexidade", json.get(15));
			atual.put("disciplina", json.getInt(16));
			atual.put("idTrfGuia", json.getInt(17));
			
			
			tarefasUsu.add(atual);			
		}
		
		
		em.close();
		entityManagerFactory.close();
		return tarefasUsu;				
	}
	
	public void deletaTarefa(int idTrf) {
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		String sql = "DELETE FROM tarefa_of where id = :idTrf";
		Query query = em.createNativeQuery(sql);
		query.setParameter("idTrf", idTrf);		
		
		em.getTransaction().begin();
		query.executeUpdate();
		em.getTransaction().commit();		
		
		em.close();
		entityManagerFactory.close();	
	}
	
	
	public void alteraSituacaoTarefa(int idTrf, int idSit) {
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		String sql = "UPDATE tarefa_of set fk_situacao = :idSit, dt_alteracao = current_timestamp() where id = :idTrf";
		Query query = em.createNativeQuery(sql);
		
		query.setParameter("idTrf", idTrf);		
		query.setParameter("idSit", idSit);
		
		em.getTransaction().begin();
		query.executeUpdate();
		em.getTransaction().commit();		
		
		em.close();
		entityManagerFactory.close();	
	}
	
	
}



























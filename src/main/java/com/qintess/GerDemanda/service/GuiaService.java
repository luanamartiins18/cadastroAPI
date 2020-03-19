package com.qintess.GerDemanda.service;
import javax.persistence.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GuiaService {
	
	public void atualizaTarefaGuia(String json) {
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();			
		JSONObject tarefa = new JSONObject(json);
		
		try {
			em.getTransaction().begin();
			
			String descTarefa = converteAtributoSQLStr(tarefa, "descricao_tarefa");
			String plataforma = converteAtributoSQLStr(tarefa, "plataforma");
			int id 	      = tarefa.getInt("id_tarefa");		
			
			String sqlTarefa = "UPDATE tarefa_guia set plataforma = :plataforma, descricao = :descricao where id = :id";
			Query queryTarefa = em.createNativeQuery(sqlTarefa); 			
			
			queryTarefa.setParameter("plataforma", plataforma == "null" ? null : plataforma);
			queryTarefa.setParameter("descricao", descTarefa == "null" ? null : descTarefa);
			queryTarefa.setParameter("id", id);				
			
			JSONArray itens = tarefa.getJSONArray("itens");
			
			for(int i=0; i<itens.length(); i++) {				
				
				JSONObject item = (JSONObject)itens.get(i);
	
				String sqlItem = "UPDATE item_guia set valor = :valor, componente = :componente, limite_itens = :quantidade, "
							  		+ "descricao_complex = :descricao where id = :id ;";
				
				Query query = em.createNativeQuery(sqlItem);
				
				String componente  = converteAtributoSQLStr(item, "componente");
				String descComplex = converteAtributoSQLStr(item, "descricao_complex");
				int quantidade     = converteAtributoSQLInt(item, "quantidade");
				
				query.setParameter("valor", item.getFloat("valor"));
				query.setParameter("quantidade", quantidade == 0 ? null : quantidade);				
				query.setParameter("componente", componente == "null" ? null : componente);
				query.setParameter("descricao", descComplex == "null" ? null : descComplex);				
				
				query.setParameter("id", item.getInt("id_item"));
				
				query.executeUpdate();			
			}			
			
			queryTarefa.executeUpdate();
			em.getTransaction().commit();
			
			
		}catch(Exception excp) {
			
			System.out.println(excp.getMessage());
			em.getTransaction().rollback();
			
		}finally {
			
			em.close();
			entityManagerFactory.close();
			
		}	

	}
	
	public String converteAtributoSQLStr(JSONObject tarefa, String key) {
		String res = "";
		
		try {
			
			if(tarefa.getJSONObject(key).length() == 0) res =  "null";
			
		}catch(JSONException excp){
			
			if(tarefa.getString(key).toLowerCase().equals("n/a") || tarefa.getString(key).equals("")) res = "null";
			else res = tarefa.getString(key);		
			
		}
		
		return res;	
	}		
	
	public int converteAtributoSQLInt(JSONObject tarefa, String key) {
		int res = 0;
		
		try {
			
			if(tarefa.getJSONObject(key).length() == 0) res =  0;
			
		}catch(JSONException excp){		
			
			res = tarefa.getInt(key);
			
		}
		
		return res;	
	}	

	public void insereTarefaGuia(String param) {
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		JSONObject json = new JSONObject(param);
		
		int disciplina = json.getInt("disciplina");
		String atividade = json.getString("atividade");
		String plataforma = json.getString("plataforma");
		String descTarefa = json.getString("descTarefa");
		

		JSONArray itens = json.getJSONArray("itens");
		for(int i=0; i<itens.length(); i++) {
			JSONObject item = (JSONObject)itens.get(i);
			System.out.println(item);
		}
		
			
		
		
		em.close();
		entityManagerFactory.close();
		
	}
	
	public List<HashMap<String, Object>> getAtividades(){
		
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("PU");
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		
		String sql = "select distinct atividade from tarefa_guia " + 
						"order by fk_disciplina, cast(substring_index( substring_index(atividade, ' ', 1), '.', -1 ) as unsigned)";
		
		Query query = em.createNativeQuery(sql);
		List<Object> listaAtividades = query.getResultList();
		
		List<HashMap<String, Object>> res = new ArrayList<HashMap<String,Object>>();
		
		for(Object i: listaAtividades) {
			HashMap<String, Object> atual = new HashMap<String, Object>();
			atual.put("atividade", i);
			res.add(atual);
		}
		
		em.close();
		entityManagerFactory.close();
		return res;		
	}
	
}
	















package com.qintess.GerDemanda.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.qintess.GerDemanda.PersistenceHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Usuario;

public class MensagemService {
	
	public List<HashMap<String, Object>> getAllMensagensColaborador(int id){
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();	
	
		String sql = "select m.*, um.dt_leitura, um.id as idUM, s.descricao, u.nome from mensagem m " + 
						"inner join usuario_x_mensagem um " + 
						"on m.id = um.fk_mensagem " + 	
						"left join sigla s " +
						"on s.id = m.fk_sigla " +
						"inner join usuario u " +
						"on u.id = m.fk_responsavel " +
						"where m.status = 1 " + 
						"and um.fk_usuario = :id ;";
		
		Query query = em.createNativeQuery(sql).setParameter("id", id);
		
		List<Object> lista = query.getResultList();	
		
		List<HashMap<String, Object>> response = new ArrayList<HashMap<String,Object>>();
		
		for(Object obj: lista) {
			HashMap<String, Object> atual = new HashMap<String, Object>();
			JSONArray objAtual = new JSONArray(obj);
			
			atual.put("idMsg", objAtual.get(0));
			atual.put("corpo", objAtual.get(1));
			atual.put("dtCriacao", objAtual.get(2));
			atual.put("dtExpiracao", objAtual.get(3));
			atual.put("tpMsg", objAtual.get(4));
			atual.put("status", objAtual.get(5));
			atual.put("responsavel", objAtual.get(12));		
			atual.put("titulo", objAtual.get(7));
			atual.put("idUsuMsg", objAtual.get(10));	
			atual.put("sigla", objAtual.get(11));
			
			response.add(atual);
		}
				
		em.close();
		return response;
	}	
	
	
	
	public void marcaLida(int idMsgUsu) {
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();					
		
		String sql = "UPDATE usuario_x_mensagem set dt_leitura = current_timestamp() where id = :idMsgUsu ;";
		Query query = em.createNativeQuery(sql);
		query.setParameter("idMsgUsu", idMsgUsu);
		
		em.getTransaction().begin();
		query.executeUpdate();	
		em.getTransaction().commit();
		
		em.close();
	}
	
	public List<HashMap<String, Object>> getMensagens() {
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();					
		
		String sql = "select m.id, m.corpo, m.dt_criacao, m.dt_expiracao, m.tp_mensagem, m.status, u.nome , u2.nome as responsavel, m.titulo from mensagem m " + 
						"inner join usuario_x_mensagem um on m.id = um.fk_mensagem " + 
						"inner join usuario u on um.fk_usuario = u.id " + 
						"inner join usuario u2 on m.fk_responsavel = u2.id "
						+ "order by m.dt_expiracao asc;" ;
			
		Query query = em.createNativeQuery(sql);
		List<Object> lista = query.getResultList();
		
		List<HashMap<String, Object>> response = new ArrayList<HashMap<String,Object>>();
		
		for(Object obj: lista) {
			HashMap<String, Object> atual = new HashMap<String, Object>();
			JSONArray objAtual = new JSONArray(obj);			
			atual.put("idMsg", objAtual.get(0));
			atual.put("corpo", objAtual.get(1));
			atual.put("dtCriacao", objAtual.get(2));
			atual.put("dtExpiracao", objAtual.get(3));
			atual.put("tpMsg", objAtual.get(4));
			atual.put("status", objAtual.get(5));
			atual.put("usuario", objAtual.get(6));
			atual.put("responsavel", objAtual.get(7));
			atual.put("titulo", objAtual.get(8));
			response.add(atual);
		}
			
		em.close();
		return response;		
	}
	
	public void insereMensagemGeral(String corpo, int idResponsavel, String dtExp, String titulo) {
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		UsuarioService usuarioService = new UsuarioService();
		List<Usuario> listUsu = usuarioService.getUsuariosAtivos();
		
		em.getTransaction().begin();
		
		try {
			String sql = "INSERT INTO MENSAGEM (corpo, dt_criacao, dt_expiracao, tp_mensagem, status, fk_responsavel, titulo) VALUES ( '"
					+ corpo + "', CURRENT_TIMESTAMP(), '" + dtExp + "', 'GERAL', 1, " 
					+ Integer.toString(idResponsavel) + ",'" + titulo + "');";
			System.out.println(sql);
	
			Query query = em.createNativeQuery(sql);
			query.executeUpdate();
			
			
			for(Usuario usu: listUsu) {
				
				sql = "INSERT INTO usuario_x_mensagem(fk_usuario, fk_mensagem, dt_leitura) VALUES ("
						+ Integer.toString(usu.getId()) + ",  (SELECT MAX(id) from mensagem), null);";
				
				query = em.createNativeQuery(sql);
				query.executeUpdate();				
			}			
			
	
		}catch(Exception excp) {
			em.getTransaction().rollback();
		}		
		
		em.getTransaction().commit();
		em.close();
	}
	
	public void insereMensagemSigla(String corpo, int idResponsavel, String dtExp, int idSigla, String titulo) {
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();	
		
		UsuarioService usuarioService = new UsuarioService();
		List<Usuario> listUsu = usuarioService.getUsuarioBySigla(idSigla);
		
		em.getTransaction().begin();
		
		try {
			String sql = "INSERT INTO mensagem (corpo, dt_criacao, dt_expiracao, tp_mensagem, status, fk_responsavel, titulo, fk_sigla) VALUES ( '"
					+ corpo + "', CURRENT_TIMESTAMP(), '" + dtExp + "', 'SIGLA', 1, " + Integer.toString(idResponsavel) 
					+ ",'" + titulo + "'," + Integer.toString(idSigla) + ");";
			
			
			System.out.println(sql);
	
			Query query = em.createNativeQuery(sql);
			query.executeUpdate();
			
			
			for(Usuario usu: listUsu) {
				
				sql = "INSERT INTO usuario_x_mensagem(fk_usuario, fk_mensagem, dt_leitura) VALUES ("
						+ Integer.toString(usu.getId()) + ",  (SELECT MAX(id) from mensagem), null);";
				
				query = em.createNativeQuery(sql);
				query.executeUpdate();				
			}			
			
	
		}catch(Exception excp) {
			em.getTransaction().rollback();
		}		
		
		em.getTransaction().commit();
		em.close();
	}
	
	public List<HashMap<String, Object>> getMensagensColaborador(int id){
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();	
	
		String sql = "select m.*, um.dt_leitura, um.id as idUM, s.descricao, u.nome from mensagem m " + 
						"inner join usuario_x_mensagem um " + 
						"on m.id = um.fk_mensagem " + 	
						"left join sigla s " +
						"on s.id = m.fk_sigla " +
						"inner join usuario u " +
						"on u.id = m.fk_responsavel " +
						"where m.status = 1 and  dt_leitura is null and curdate() <= m.dt_expiracao " + 
						"and um.fk_usuario = :id ;";
		
		Query query = em.createNativeQuery(sql).setParameter("id", id);
		
		List<Object> lista = query.getResultList();	
		
		List<HashMap<String, Object>> response = new ArrayList<HashMap<String,Object>>();
		
		for(Object obj: lista) {
			HashMap<String, Object> atual = new HashMap<String, Object>();
			JSONArray objAtual = new JSONArray(obj);
			
			atual.put("idMsg", objAtual.get(0));
			atual.put("corpo", objAtual.get(1));
			atual.put("dtCriacao", objAtual.get(2));
			atual.put("dtExpiracao", objAtual.get(3));
			atual.put("tpMsg", objAtual.get(4));
			atual.put("status", objAtual.get(5));
			atual.put("responsavel", objAtual.get(12));		
			atual.put("titulo", objAtual.get(7));
			atual.put("idUsuMsg", objAtual.get(10));	
			atual.put("sigla", objAtual.get(11));
			
			response.add(atual);
		}
				
		em.close();
		return response;
	}	
	
	public List<HashMap<String, Object>> listaMensagens(){
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();	
	
		String sql = "select m.*, u.nome, s.descricao from mensagem m "
						+ "inner join usuario u on m.fk_responsavel = u.id "
						+ " left join sigla s on s.id = m.fk_sigla "
						+ "order by m.status desc, m.dt_expiracao asc;"; 
						
		
		Query query = em.createNativeQuery(sql);
		
		List<Object> lista = query.getResultList();	
		
		List<HashMap<String, Object>> response = new ArrayList<HashMap<String,Object>>();
		
		for(Object obj: lista) {
			HashMap<String, Object> atual = new HashMap<String, Object>();
			JSONArray objAtual = new JSONArray(obj);
			
			atual.put("idMsg", objAtual.get(0));
			atual.put("corpo", objAtual.get(1));
			atual.put("dtCriacao", objAtual.get(2));
			atual.put("dtExpiracao", objAtual.get(3));
			atual.put("tpMsg", objAtual.get(4));
			atual.put("status", objAtual.get(5));
			atual.put("responsavel", objAtual.get(9));	
			atual.put("titulo", objAtual.get(7));
			atual.put("sigla", objAtual.get(10));			
			response.add(atual);
		}
				
		em.close();
		return response;
	}	
	
	public List<HashMap<String, Object>> detalhaMensagem(int id){
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();	
	
		String sql = "select m.id as idM, m.corpo, m.dt_criacao, m.dt_expiracao, m.tp_mensagem, m.status, m.titulo," + 
						"um.id as idUM, um.dt_leitura, u.id as idU, u.nome " + 
						"from mensagem m " + 
						"inner join usuario_x_mensagem um on m.id = um.fk_mensagem " +
						"inner join usuario u on um.fk_usuario = u.id " + 
						"where m.id = " + Integer.toString(id); 
						
		
		Query query = em.createNativeQuery(sql);
		
		List<Object> lista = query.getResultList();	
		
		List<HashMap<String, Object>> response = new ArrayList<HashMap<String,Object>>();
		
		for(Object obj: lista) {
			HashMap<String, Object> atual = new HashMap<String, Object>();
			JSONArray objAtual = new JSONArray(obj);
			
			atual.put("idM", objAtual.get(0));
			atual.put("corpo", objAtual.get(1));
			atual.put("dtCriacao", objAtual.get(2));
			atual.put("dtExpiracao", objAtual.get(3));
			atual.put("tpMsg", objAtual.get(4));
			atual.put("status", objAtual.get(5));
			atual.put("titulo", objAtual.get(6));	
			atual.put("idUM", objAtual.get(7));
			atual.put("dtLeitura", objAtual.get(8));	
			atual.put("idU", objAtual.get(9));
			atual.put("nomeUsu", objAtual.get(10));		
			
			response.add(atual);
		}
				
		em.close();

		
		return response;
	}
	
	public void alteraStatusMsg(int idMsg, String acao) {
		
		EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();					
		String sql = "";
		
		if(acao.equals("desativar")) {
			sql = "UPDATE mensagem set status = 0 where id = :id";
		}else {
			sql = "UPDATE mensagem set status = 1 where id = :id";
		}
		
		
		Query query = em.createNativeQuery(sql);
		query.setParameter("id", idMsg);
		
		em.getTransaction().begin();
		query.executeUpdate();	
		em.getTransaction().commit();
		
		em.close();
	}	
}


































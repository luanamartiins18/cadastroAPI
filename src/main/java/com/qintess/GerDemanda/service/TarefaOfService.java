package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.PersistenceHelper;
import com.qintess.GerDemanda.model.TarefaOf;
import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.repositories.TarefaOfRepository;
import com.qintess.GerDemanda.repositories.UsuarioOrdemFornecimentoRepository;
import org.hibernate.ObjectNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service

public class TarefaOfService {

    @Autowired
    TarefaOfRepository tarefaOfRepository;

    @Autowired
    OrdemFornecimentoService ordemFornecimentoService;

    public TarefaOf findById(Integer id) {
        return tarefaOfRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", TarefaOf.class.getName()));
    }

    public void deletaTarefa(Integer id) {
        TarefaOf tarefaOf = this.findById(id);
        tarefaOfRepository.delete(tarefaOf);
    }

    public boolean insereTarefa(String param) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        JSONObject json = new JSONObject(param);
        System.out.println(json);
        String historia = json.getString("historia");
        String sprint = json.getString("sprint");
        String observacao = json.getString("observacao");
        String artefato = json.getString("artefato");
        Integer quantidade;
        try {
            quantidade = json.getInt("quantidade");
        } catch (Exception excp) {
            quantidade = -1;
        }
        int idUsu = json.getInt("usu");
        int idOf = json.getInt("of");
        int numTarefa = json.getInt("numTarefa");
        int idItem = json.getInt("numItemGuia");
        int auxPerfil = json.getInt("perfil");
        String perfil = (auxPerfil == 1) ? "Baixa" : "Alta";
        UsuarioOrdemFornecimento usuOf = ordemFornecimentoService.getIdUsuOf(idUsu, idOf);
        String sql = "insert into tarefa_of(historia, sprint, dt_inclusao, dt_alteracao, num_tarefa, perfil, quantidade, artefato, observacao, fk_situacao, fk_item_guia, fk_of_usuario) " +
                "values (:historia , :sprint , current_timestamp(), null, :numTarefa, :perfil, :quantidade , :artefato , :observacao , 6, :idItem, :usuOf );";
        Query query = em.createNativeQuery(sql);
        query.setParameter("historia", historia);
        query.setParameter("sprint", sprint);
        query.setParameter("quantidade", quantidade == -1 ? null : quantidade);
        query.setParameter("artefato", artefato);
        query.setParameter("observacao", observacao);
        query.setParameter("idItem", idItem);
        query.setParameter("usuOf", usuOf.getId());
        query.setParameter("numTarefa", numTarefa);
        query.setParameter("perfil", perfil);
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public void atualizaTarefa(String param) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        JSONObject json = new JSONObject(param);
        String historia = json.getString("historia");
        String sprint = json.getString("sprint");
        String observacao = json.getString("observacao");
        String artefato = json.getString("artefato");
        int item = json.getInt("item");
        int quantidade = json.getInt("quantidade");
        int numTarefa = json.getInt("numTarefa");
        int idItem = json.getInt("numItemGuia");
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
    }

    public HashMap<String, Integer> getValorTarefa(int idUsu, int idOf) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
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
        for (Object obj : listaValorSit) {
            JSONArray json = new JSONArray(obj);
            int valor = json.getInt(0);
            int situ = json.getInt(1);
            if (situ == 4 || situ == 8) {
                valorExecutado += valor;
            }
            if (situ != 2 && situ != 5) {
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
        for (Object obj : listaValorSit) {
            JSONArray json = new JSONArray(obj);
            int valor = json.getInt(0);
            int situ = json.getInt(1);
            if (situ == 4 || situ == 8) {
                valorExecutado += valor;
            }
            if (situ != 2 && situ != 5) {
                valorPlanejado += valor;
            }
        }
        resultado.put("valorExecutadoTotal", valorExecutado);
        resultado.put("valorPlanejadoTotal", valorPlanejado);
        em.close();
        return resultado;
    }

    public List<HashMap<String, Object>> getTarefasUsuario(int idUsu, int idOf) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select t.* , s.descricao, ig.valor, cg.descricao as complexidade, tg.fk_disciplina, tg.id as idTrf, ig.componente, tg.tarefa as tarefa from tarefa_of t " +
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
        for (Object obj : res) {
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
            atual.put("componente", json.get(18));
            atual.put("tarefa", json.get(19));
            tarefasUsu.add(atual);
        }
        em.close();
        return tarefasUsu;
    }

    public void alteraSituacaoTarefa(int idTrf, int idSit) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "UPDATE tarefa_of set fk_situacao = :idSit, dt_alteracao = current_timestamp() where id = :idTrf";
        Query query = em.createNativeQuery(sql);
        query.setParameter("idTrf", idTrf);
        query.setParameter("idSit", idSit);
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
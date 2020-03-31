package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.PersistenceHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service

public class TarefaService {


    public List<HashMap<String, Object>> getAtividades() {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "select tarefa, fk_disciplina from tarefa_guia";

        Query query = em.createNativeQuery(sql);

        List<Object> res = query.getResultList();
        List<HashMap<String, Object>> disciplinas = new ArrayList<HashMap<String, Object>>();

        for (Object obj : res) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray json = new JSONArray(obj);

            atual.put("id", json.get(0));
            atual.put("descricao", json.get(1));
            atual.put("perfil", json.get(2));

            disciplinas.add(atual);
        }


        em.close();

        return disciplinas;
    }


    public List<HashMap<String, Object>> getItensGuia() {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "SELECT tg.*, um.descricao as desc_uni_med, ig.id as id_item, ig.limite_itens, ig.componente, cg.descricao as desc_complex, cg.id as id_complex, ig.descricao_complex, ig.valor  from tarefa_guia tg\r\n" +
                "	inner join item_guia ig " +
                "		on ig.fk_tarefa_guia = tg.id " +
                "	inner join uni_medida um " +
                "		on um.id = ig.fk_uni_medida " +
                "	inner join complex_guia cg " +
                "		on cg.id = ig.fk_complex_guia "
                + "	order by tg.id ";


        Query query = em.createNativeQuery(sql);
        List<Object> res = query.getResultList();

        String tarefaAnterior = "";
        List<HashMap<String, Object>> guia = new ArrayList<HashMap<String, Object>>();

        for (Object obj : res) {

            /*
             * Cada iteração insere um item novo na ultima tarefa adicionada
             * Uma tarefa é adicionada quando o número da tarefa muda entre as linhas
             * */

            JSONArray json = new JSONArray(obj);
            //Se a tarefa atual for diferente da tarefa anterior
            if (!json.getString(3).equals(tarefaAnterior)) {
                HashMap<String, Object> atual = new HashMap<String, Object>();

                atual.put("id_tarefa", json.get(0));
                atual.put("plataforma", json.get(1));
                atual.put("atividade", json.get(2));
                atual.put("tarefa", json.get(3));
                atual.put("descricao_tarefa", json.get(4));
                atual.put("disciplina", json.get(5));
                atual.put("uni_medida", json.get(6));

                atual.put("itens", new ArrayList<HashMap<String, Object>>());

                guia.add(atual);
            }

            HashMap<String, Object> ultimoInserido = guia.get(guia.size() - 1);
            List<HashMap<String, Object>> itens = (ArrayList<HashMap<String, Object>>) ultimoInserido.get("itens");

            HashMap<String, Object> itemGuia = new HashMap<String, Object>();
            itemGuia.put("id_item", json.get(7));
            itemGuia.put("quantidade", json.get(8));
            itemGuia.put("componente", json.get(9));
            itemGuia.put("complexidade", json.get(10));
            itemGuia.put("id_complex", json.get(11));
            itemGuia.put("descricao_complex", json.get(12));
            itemGuia.put("valor", json.get(13));

            itens.add(itemGuia);
            tarefaAnterior = json.getString(3);
        }

        em.close();

        return guia;
    }

    public List<HashMap<String, Object>> getDisciplinas() {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "select d.id, d.descricao, p.descricao as p_descricao from disciplina d " +
                "inner join perfil p " +
                "on d.fk_perfil = p.id ";

        Query query = em.createNativeQuery(sql);

        List<Object> res = query.getResultList();
        List<HashMap<String, Object>> disciplinas = new ArrayList<HashMap<String, Object>>();

        for (Object obj : res) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray json = new JSONArray(obj);

            atual.put("id", json.get(0));
            atual.put("descricao", json.get(1));
            atual.put("perfil", json.get(2));

            disciplinas.add(atual);
        }


        em.close();

        return disciplinas;
    }

    public List<HashMap<String, Object>> getComplexidades() {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "select * from complex_guia ; ";

        Query query = em.createNativeQuery(sql);

        List<Object> res = query.getResultList();
        List<HashMap<String, Object>> complexidades = new ArrayList<HashMap<String, Object>>();

        for (Object obj : res) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray json = new JSONArray(obj);

            atual.put("id", json.get(0));
            atual.put("descricao", json.get(1));

            complexidades.add(atual);
        }


        em.close();

        return complexidades;
    }

    public List<HashMap<String, Object>> getUniMedidas() {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "select * from uni_medida order by descricao";

        Query query = em.createNativeQuery(sql);

        List<Object> res = query.getResultList();
        List<HashMap<String, Object>> uniMedidas = new ArrayList<HashMap<String, Object>>();

        for (Object obj : res) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray json = new JSONArray(obj);

            atual.put("id", json.get(0));
            atual.put("descricao", json.get(1));

            uniMedidas.add(atual);
        }


        em.close();

        return uniMedidas;
    }

    public String getNumOf(int id) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "select numero_of_genti from ordem_forn where id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);

        JSONArray json = new JSONArray(query.getResultList());

        em.close();

        return json.getString(0);
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
        String quantidade = json.getString("quantidade");
        int idUsu = json.getInt("usu");
        int idOf = json.getInt("of");
        int numTarefa = json.getInt("numTarefa");
        int idItem = json.getInt("numItemGuia");
        int auxPerfil = json.getInt("perfil");
        String perfil = (auxPerfil == 1) ? "Baixa" : "Alta";

        int usuOf = getIdUsuOf(idUsu, idOf);

        String sql = "insert into tarefa_of(historia, sprint, dt_inclusao, dt_alteracao, num_tarefa, perfil, quantidade, artefato, observacao, fk_situacao, fk_item_guia, fk_of_usuario) " +
                "values (:historia , :sprint , current_timestamp(), null, :numTarefa, :perfil, :quantidade , :artefato , :observacao , 6, :idItem, :usuOf );";

        Query query = em.createNativeQuery(sql);

        query.setParameter("historia", historia);
        query.setParameter("sprint", sprint);
        query.setParameter("quantidade", quantidade.equals("") ? null : quantidade);
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

    private int getIdUsuOf(int usu, int of) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "select id from usuario_x_of where fk_usuario = :usu and fK_ordem_forn = :of and status = 1";
        Query query = em.createNativeQuery(sql);

        query.setParameter("usu", usu);
        query.setParameter("of", of);

        List<Object> usuOf = query.getResultList();
        JSONArray json = new JSONArray(usuOf);

        em.close();


        return json.getInt(0);
    }


    public List<HashMap<String, Object>> getTarefasUsuario(int idUsu, int idOf) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "select t.* , s.descricao, ig.valor, cg.descricao as complexidade, tg.fk_disciplina, tg.id as idTrf, ig.componente from tarefa_of t " +
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


            tarefasUsu.add(atual);
        }


        em.close();

        return tarefasUsu;
    }

    public void deletaTarefa(int idTrf) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "DELETE FROM tarefa_of where id = :idTrf";
        Query query = em.createNativeQuery(sql);
        query.setParameter("idTrf", idTrf);

        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();

        em.close();

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



























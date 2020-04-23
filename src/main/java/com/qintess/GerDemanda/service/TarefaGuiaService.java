package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.PersistenceHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TarefaGuiaService {

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
                + "	order by " +
                "tg.fk_disciplina, " +
                "cast(substring_index( substring_index(tg.atividade, ' ', 1), '.', -1 ) as unsigned)," +
                "cast(substring_index(tg.tarefa, '.', -1) as unsigned) ";
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

    public void atualizaTarefaGuia(String json) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        JSONObject tarefa = new JSONObject(json);
        try {
            em.getTransaction().begin();
            String descTarefa = converteAtributoSQLStr(tarefa, "descricao_tarefa");
            String plataforma = converteAtributoSQLStr(tarefa, "plataforma");
            int id = tarefa.getInt("id_tarefa");
            String sqlTarefa = "UPDATE tarefa_guia set plataforma = :plataforma, descricao = :descricao where id = :id";
            Query queryTarefa = em.createNativeQuery(sqlTarefa);
            queryTarefa.setParameter("plataforma", plataforma == "null" ? null : plataforma);
            queryTarefa.setParameter("descricao", descTarefa == "null" ? null : descTarefa);
            queryTarefa.setParameter("id", id);
            JSONArray itens = tarefa.getJSONArray("itens");
            for (int i = 0; i < itens.length(); i++) {
                JSONObject item = (JSONObject) itens.get(i);
                String sqlItem = "UPDATE item_guia set valor = :valor, componente = :componente, limite_itens = :quantidade, "
                        + "descricao_complex = :descricao where id = :id ;";
                Query query = em.createNativeQuery(sqlItem);
                String componente = converteAtributoSQLStr(item, "componente");
                String descComplex = converteAtributoSQLStr(item, "descricao_complex");
                int quantidade = converteAtributoSQLInt(item, "quantidade");
                query.setParameter("valor", item.getFloat("valor"));
                query.setParameter("quantidade", quantidade == -1 ? null : quantidade);
                query.setParameter("componente", componente == "null" ? null : componente);
                query.setParameter("descricao", descComplex == "null" ? null : descComplex);
                query.setParameter("id", item.getInt("id_item"));
                query.executeUpdate();
            }
            queryTarefa.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public String converteAtributoSQLStr(JSONObject tarefa, String key) {
        String res = "";
        try {
            if (tarefa.getJSONObject(key).length() == 0) res = "null";
        } catch (JSONException excp) {
            if (tarefa.getString(key).toLowerCase().equals("n/a") || tarefa.getString(key).equals("")) res = "null";
            else res = tarefa.getString(key);
        }
        return res;
    }

    public int converteAtributoSQLInt(JSONObject tarefa, String key) {
        int res = -1;
        try {
            if (tarefa.getJSONObject(key).length() == 0) res = -1;
        } catch (JSONException excp) {
            res = tarefa.getInt(key);
        }
        return res;
    }

    public boolean atividadeJaExiste(String atividade) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select distinct atividade from tarefa_guia " +
                "order by fk_disciplina, cast(substring_index( substring_index(atividade, ' ', 1), '.', -1 ) as unsigned)";
        Query query = em.createNativeQuery(sql);
        List<Object> listaAtividades = query.getResultList();
        for (Object i : listaAtividades) {
            String trfAtual = (String) i;
            if (atividade.equals(trfAtual)) {
                em.close();
                return true;
            }
        }
        em.close();
        return false;
    }

    public Integer criaNumeroAtividade(int idDisciplina) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select distinct atividade from tarefa_guia " +
                "	where fk_disciplina = :idDisciplina " +
                "   order by fk_disciplina, cast(substring_index( substring_index(atividade, ' ', 1), '.', -1 ) as unsigned)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("idDisciplina", idDisciplina);
        List<Object> listaAtividades = query.getResultList();
        String ultimaAtividade = (String) listaAtividades.get(listaAtividades.size() - 1);
        int comeco = ultimaAtividade.indexOf(".") + 1;
        int fim = ultimaAtividade.indexOf(" ");
        int numAtividade = Integer.parseInt(ultimaAtividade.substring(comeco, fim)) + 1;
        em.close();
        return numAtividade;
    }

    public Integer criaNumeroTarefa(int idDisciplina, String atividade) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        //Valor default para quando a atividade está sendo criada e não possui nenhuma tarefa ainda
        int numTrf = 1;
        int fimNumAtividade = atividade.indexOf(" ");
        atividade = atividade.substring(0, fimNumAtividade);
        String sql = "select distinct tarefa from tarefa_guia " +
                "	where fk_disciplina = :idDisciplina and" +
                "   tarefa like '" + atividade + "%' " +
                "   order by fk_disciplina, cast(substring_index( substring_index(atividade, ' ', 1), '.', -1 ) as unsigned)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("idDisciplina", idDisciplina);
        List<Object> listaTarefas = query.getResultList();
        if (!listaTarefas.isEmpty()) {
            String ultimaTarefa = (String) listaTarefas.get(listaTarefas.size() - 1);
            //Informando pro regex considerar o ponto como caracter e não como expressão
            String[] splitTrf = ultimaTarefa.split("\\.");
            numTrf = Integer.parseInt(splitTrf[splitTrf.length - 1]) + 1;
        }
        em.close();
        return numTrf;
    }

    public String getNumeroAtividade(String atividade) {
        int primeiro = atividade.indexOf(".") + 1;
        int ultimo = atividade.indexOf(" ");
        return atividade.substring(primeiro, ultimo);
    }

    public void insereTarefaGuia(String param) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            JSONObject json = new JSONObject(param);
            Integer disciplina = json.getInt("disciplina");
            String atividade = json.getString("atividade");
            String descTarefa = json.getString("descTarefa");
            String plataforma = json.getString("plataforma");
            plataforma = plataforma.equals("") ? null : plataforma;
            if (!atividadeJaExiste(atividade)) {
                atividade = disciplina.toString() + '.' + criaNumeroAtividade(disciplina).toString() + "    " + atividade;
            }
            String numTrf = disciplina.toString() + '.' + getNumeroAtividade(atividade) + '.' + Integer.toString(criaNumeroTarefa(disciplina, atividade));
            String sql = "INSERT INTO tarefa_guia(plataforma, atividade, tarefa, descricao, fk_disciplina) "
                    + "VALUES(:plataforma, :atividade, :tarefa, :descricao, :fk_disciplina) ;";
            Query query = em.createNativeQuery(sql);
            query.setParameter("plataforma", plataforma);
            query.setParameter("descricao", descTarefa);
            query.setParameter("fk_disciplina", disciplina);
            query.setParameter("atividade", atividade);
            query.setParameter("tarefa", numTrf);
            query.executeUpdate();
            JSONArray itens = json.getJSONArray("itens");
            for (int i = 0; i < itens.length(); i++) {
                JSONObject item = (JSONObject) itens.get(i);
                int complexidade = item.getInt("complexidade");
                int uniMedida = item.getInt("uni_medida");
                int quantidade = item.getInt("quantidade");
                String componente = item.getString("componente");
                float valor = item.getFloat("valor");
                String descComplex = item.getString("desc_complex");
                String sqlItem = "INSERT INTO item_guia (componente, limite_itens, valor, descricao_complex, fk_uni_medida, fk_tarefa_guia, fk_complex_guia) " +
                        "VALUES ( :componente , :quantidade , :valor , :descComplex , :uniMedida , (select max(id) from tarefa_guia), :complexGuia ) ; ";
                Query queryItem = em.createNativeQuery(sqlItem);
                queryItem.setParameter("componente", componente.equals("") ? null : componente);
                queryItem.setParameter("quantidade", quantidade == -1 ? null : quantidade);
                queryItem.setParameter("valor", valor);
                queryItem.setParameter("descComplex", descComplex);
                queryItem.setParameter("uniMedida", uniMedida);
                queryItem.setParameter("complexGuia", complexidade);
                queryItem.executeUpdate();
            }
        } catch (Exception excp) {
            em.getTransaction().rollback();
            em.close();
            throw excp;
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<HashMap<String, Object>> getAtividades() {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select distinct atividade, fk_disciplina from tarefa_guia " +
                "order by fk_disciplina, cast(substring_index( substring_index(atividade, ' ', 1), '.', -1 ) as unsigned)";
        Query query = em.createNativeQuery(sql);
        List<Object> listaAtividades = query.getResultList();
        List<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
        for (Object i : listaAtividades) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray json = new JSONArray(i);
            atual.put("atividade", json.get(0));
            atual.put("disciplina", json.get(1));
            res.add(atual);
        }
        em.close();
        return res;
    }
}
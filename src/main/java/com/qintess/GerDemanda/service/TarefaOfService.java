package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.PersistenceHelper;
import com.qintess.GerDemanda.model.ItemGuia;
import com.qintess.GerDemanda.model.Situacao;
import com.qintess.GerDemanda.model.TarefaOf;
import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.repositories.TarefaOfRepository;
import com.qintess.GerDemanda.service.dto.TarefaOfDTO;
import com.qintess.GerDemanda.service.dto.TarefaOfDetalhadoDTO;
import com.qintess.GerDemanda.service.mapper.TarefaOfDetalhadoMapper;
import com.qintess.GerDemanda.service.mapper.TarefaOfMapper;
import org.hibernate.ObjectNotFoundException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service

public class TarefaOfService {

    @Autowired
    TarefaOfRepository tarefaOfRepository;

    @Autowired
    OrdemFornecimentoService ordemFornecimentoService;

    @Autowired
    TarefaOfMapper tarefaOfMapper;

    @Autowired
    TarefaOfDetalhadoMapper tarefaOfDetalhadoMapper;


    public List<TarefaOfDetalhadoDTO> getTarefasUsuario(Integer idUsu, Integer idOf) {
        return tarefaOfDetalhadoMapper.toDto(tarefaOfRepository
                .findByUsuarioOrdemFornecimentoUsuarioIdAndUsuarioOrdemFornecimentoOrdemFornecimentoId(idUsu, idOf));
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

    public TarefaOf findById(Integer id) {
        return tarefaOfRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", TarefaOf.class.getName()));
    }

    public void deletaTarefa(Integer id) {
        TarefaOf tarefaOf = this.findById(id);
        tarefaOfRepository.delete(tarefaOf);
    }

    public void alteraSituacaoTarefa(Integer idTrf, Integer idSit) {
        TarefaOf tarefaOf = this.findById(idTrf);
        tarefaOf.setSituacao(Situacao.builder().id(idSit).build());
        tarefaOfRepository.save(tarefaOf);
    }

    @Transactional
    public boolean insereTarefa(TarefaOfDTO dto) {
        TarefaOf obj = tarefaOfMapper.toEntity(dto);
        obj.setSituacao(Situacao.builder().id(6).build());
        obj.setPerfil((obj.getPerfil().equals('1')) ? "Baixa" : "Alta");
        Integer idUsu = obj.getUsuarioOrdemFornecimento().getUsuario().getId();
        Integer idOf = obj.getUsuarioOrdemFornecimento().getOrdemFornecimento().getId();
        UsuarioOrdemFornecimento usuOf = ordemFornecimentoService.getIdUsuOf(idUsu, idOf);
        obj.setUsuarioOrdemFornecimento(usuOf);
        tarefaOfRepository.save(obj);
        return true;
    }

    private void tarefaOfMapperUpdate(TarefaOfDTO dto, TarefaOf objOld) {
        TarefaOf objNew = tarefaOfMapper.toEntity(dto);
        objOld.setHistoria(objNew.getHistoria());
        objOld.setSprint(objNew.getSprint());
        objOld.setObservacao(objNew.getObservacao());
        objOld.setArtefato(objNew.getArtefato());
        objOld.setQuantidade(objNew.getQuantidade());
        objOld.setNum_tarefa(objNew.getNum_tarefa());
        objNew.setItemGuia(ItemGuia.builder().id(objNew.getItemGuia().getId()).build());
        objOld.setPerfil((objNew.getPerfil().equals('1')) ? "Baixa" : "Alta");
        objOld.setId(objNew.getId());
    }

    @Transactional
    public void atualizaTarefa(Integer idTrfOf, TarefaOfDTO dto) {
        TarefaOf objOld = findById(idTrfOf);
        tarefaOfMapperUpdate(dto, objOld);
        tarefaOfRepository.save(objOld);
    }
}
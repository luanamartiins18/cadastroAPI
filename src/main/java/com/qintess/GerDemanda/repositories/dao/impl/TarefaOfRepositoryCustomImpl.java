package com.qintess.GerDemanda.repositories.dao.impl;

import com.qintess.GerDemanda.model.*;
import com.qintess.GerDemanda.repositories.dao.TarefaOfRepositoryCustom;
import com.qintess.GerDemanda.service.dto.RelatorioDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class TarefaOfRepositoryCustomImpl implements TarefaOfRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TarefaOf> findByFilter(Map<String, String> filter) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<TarefaOf> cq = cb.createQuery(TarefaOf.class);
        final Root root = cq.from(TarefaOf.class);

        Path<ItemGuia> itemGuiaPath = root.get(TarefaOf_.itemGuia);
        Path<UsuarioOrdemFornecimento> usuarioOrdemFornecimento = root.get(TarefaOf_.usuarioOrdemFornecimento);
        Path<Usuario> usuario = usuarioOrdemFornecimento.get(UsuarioOrdemFornecimento_.usuario);
        Path<OrdemFornecimento> ordemFornecimento = usuarioOrdemFornecimento.get(UsuarioOrdemFornecimento_.ordemFornecimento);
        Path<Situacao> situacao = ordemFornecimento.get(OrdemFornecimento_.situacaoUsu);
        Path<Sigla> sigla = ordemFornecimento.get(OrdemFornecimento_.sigla);

        montaQueryRelatorio(filter, cb, cq, root);

        cq.groupBy(
                ordemFornecimento.get(OrdemFornecimento_.numeroOFGenti),
                usuario.get(Usuario_.nome),
                situacao.get(Situacao_.descricao),
                ordemFornecimento.get(OrdemFornecimento_.referencia),
                sigla.get(Sigla_.descricao)
        );
        return entityManager.createQuery(cq).getResultList();
    }


    public List<TarefaOf> findByFilterGroupSiglaAndReferencia(Map<String, String> filter) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<TarefaOf> cq = cb.createQuery(TarefaOf.class);
        final Root root = cq.from(TarefaOf.class);

        Path<UsuarioOrdemFornecimento> usuarioOrdemFornecimento = root.get(TarefaOf_.usuarioOrdemFornecimento);
        Path<OrdemFornecimento> ordemFornecimento = usuarioOrdemFornecimento.get(UsuarioOrdemFornecimento_.ordemFornecimento);
        Path<Sigla> sigla = ordemFornecimento.get(OrdemFornecimento_.sigla);

        montaQueryRelatorio(filter, cb, cq, root);

        cq.groupBy(
                ordemFornecimento.get(OrdemFornecimento_.referencia),
                sigla.get(Sigla_.descricao)
        );

        return entityManager.createQuery(cq).getResultList();
    }

    public List<TarefaOf> findByFilterGroupSiglaAndReferenciaAndNumOf(Map<String, String> filter) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<TarefaOf> cq = cb.createQuery(TarefaOf.class);
        final Root root = cq.from(TarefaOf.class);

        Path<UsuarioOrdemFornecimento> usuarioOrdemFornecimento = root.get(TarefaOf_.usuarioOrdemFornecimento);
        Path<OrdemFornecimento> ordemFornecimento = usuarioOrdemFornecimento.get(UsuarioOrdemFornecimento_.ordemFornecimento);
        Path<Sigla> sigla = ordemFornecimento.get(OrdemFornecimento_.sigla);

        montaQueryRelatorio(filter, cb, cq, root);

        cq.groupBy(
                ordemFornecimento.get(OrdemFornecimento_.numeroOFGenti),
                ordemFornecimento.get(OrdemFornecimento_.referencia),
                sigla.get(Sigla_.descricao)
        );

        return entityManager.createQuery(cq).getResultList();
    }


    private void montaQueryRelatorio(Map<String, String> filter, CriteriaBuilder cb, CriteriaQuery<TarefaOf> cq, Root root) {

        Path<ItemGuia> itemGuiaPath = root.get(TarefaOf_.itemGuia);
        Path<UsuarioOrdemFornecimento> usuarioOrdemFornecimento = root.get(TarefaOf_.usuarioOrdemFornecimento);
        Path<Usuario> usuario = usuarioOrdemFornecimento.get(UsuarioOrdemFornecimento_.usuario);
        Path<OrdemFornecimento> ordemFornecimento = usuarioOrdemFornecimento.get(UsuarioOrdemFornecimento_.ordemFornecimento);
        Path<Situacao> situacao = ordemFornecimento.get(OrdemFornecimento_.situacaoUsu);
        Path<Sigla> sigla = ordemFornecimento.get(OrdemFornecimento_.sigla);

        // parametros
        Subquery subquery = cq.subquery(Long.class);
        Root subRoot = subquery.from(OrdemFornecimento.class);
        subquery.select(cb.count(subRoot.get(OrdemFornecimento_.id)));

        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> subPredicates = new ArrayList<>();

        if (Objects.nonNull(filter.get("sigla"))) {
            predicates.add((cb.equal(sigla.get(Sigla_.descricao), filter.get("sigla"))));
        }

        if (Objects.nonNull(filter.get("numero_of"))) {
            predicates.add((cb.equal(ordemFornecimento.get(OrdemFornecimento_.numeroOFGenti), filter.get("numero_of"))));
            subPredicates.add((cb.equal(subRoot.get(OrdemFornecimento_.numeroOFGenti), filter.get("numero_of"))));
        }

        if (Objects.nonNull(filter.get("referencia"))) {
            predicates.add((cb.equal(ordemFornecimento.get(OrdemFornecimento_.referencia), filter.get("referencia"))));
            subPredicates.add((cb.equal(subRoot.get(OrdemFornecimento_.referencia), filter.get("referencia"))));
        }

        // padrao query
        predicates.add(cb.conjunction());
        predicates.add(situacao.get(Situacao_.id).in(Arrays.asList(6, 8)));

        // padrao subquery
        subPredicates.add(subRoot.get(OrdemFornecimento_.situacaoGenti).get(Situacao_.id).in(Arrays.asList(6, 8)));
        subPredicates.add(cb.equal(subRoot.get(OrdemFornecimento_.referencia), ordemFornecimento.get(OrdemFornecimento_.referencia)));
        subPredicates.add(cb.equal(subRoot.get(OrdemFornecimento_.sigla).get(Sigla_.id), sigla.get(Sigla_.id)));

        cq.where(predicates.toArray(new Predicate[0]));
        subquery.where(subPredicates.toArray(new Predicate[0]));

        // campos que irão retornar
        cq.multiselect(
                ordemFornecimento.get(OrdemFornecimento_.numeroOFGenti),
                usuario.get(Usuario_.nome),
                situacao.get(Situacao_.descricao),
                cb.sum(itemGuiaPath.get(ItemGuia_.valor).as(Double.class)),
                ordemFornecimento.get(OrdemFornecimento_.referencia),
                sigla.get(Sigla_.descricao),
                subquery.getSelection()
        );

        cq.orderBy(cb.asc(sigla.get(Sigla_.descricao)));
    }
}


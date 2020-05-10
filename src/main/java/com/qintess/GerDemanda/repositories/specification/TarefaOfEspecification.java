package com.qintess.GerDemanda.repositories.specification;

import com.qintess.GerDemanda.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;


import java.util.ArrayList;
import java.util.List;

import static javax.persistence.criteria.JoinType.INNER;
import static javax.persistence.criteria.JoinType.LEFT;

public class TarefaOfEspecification {


    public static Specification<TarefaOf> porSigla(String sigla) {
        return (root, cq, cb) -> (cb.equal(root
                .get(TarefaOf_.usuarioOrdemFornecimento)
                .get(UsuarioOrdemFornecimento_.ordemFornecimento)
                .get(OrdemFornecimento_.sigla)
                .get(Sigla_.descricao), sigla));
    }

    public static Specification<TarefaOf> porNumeroOFGenti(String numeroOFGenti) {
        return (root, cq, cb) -> (cb.equal(root
                .get(TarefaOf_.usuarioOrdemFornecimento)
                .get(UsuarioOrdemFornecimento_.ordemFornecimento)
                .get(OrdemFornecimento_.numeroOFGenti), numeroOFGenti));
    }

    public static Specification<TarefaOf> porReferencia(String referencia) {
        return (root, cq, cb) -> (cb.equal(root
                .get(TarefaOf_.usuarioOrdemFornecimento)
                .get(UsuarioOrdemFornecimento_.ordemFornecimento)
                .get(OrdemFornecimento_.referencia), referencia));
    }

    public static Specification<TarefaOf> agrupar() {
        return (root, cq, cb) -> {


            Path<ItemGuia> itemGuiaPath = root.get(TarefaOf_.itemGuia);

            Path<UsuarioOrdemFornecimento> usuarioOrdemFornecimento = root.get(TarefaOf_.usuarioOrdemFornecimento);
            Path<Usuario> usuario = usuarioOrdemFornecimento.get(UsuarioOrdemFornecimento_.usuario);
            Path<OrdemFornecimento> ordemFornecimento = usuarioOrdemFornecimento.get(UsuarioOrdemFornecimento_.ordemFornecimento);
            Path<Situacao> situacao = ordemFornecimento.get(OrdemFornecimento_.situacaoUsu);
            Path<Sigla> sigla = ordemFornecimento.get(OrdemFornecimento_.sigla);

            cq.multiselect(
                    usuario.get(Usuario_.nome),
                    situacao.get(Situacao_.descricao),
                    cb.sum(itemGuiaPath.get(ItemGuia_.valor).as(Long.class)).alias("valor"),
                    ordemFornecimento.get(OrdemFornecimento_.referencia),
                    sigla.get(Sigla_.descricao)
            );
            cq.where(cb.conjunction())
                    .groupBy(
                            ordemFornecimento.get(OrdemFornecimento_.numeroOFGenti),
                            usuario.get(Usuario_.nome),
                            situacao.get(Situacao_.descricao),
                            ordemFornecimento.get(OrdemFornecimento_.referencia),
                            sigla.get(Sigla_.descricao))
                    .orderBy(cb.desc(sigla.get(Sigla_.descricao)));

            return cq.getGroupRestriction();
        };
    }


    private static Join<?, ?> getOrCreateJoin(From<?, ?> from, String attribute) {
        return getOrCreateJoin(from, attribute, INNER);
    }

    private static Join<?, ?> getOrCreateJoin(From<?, ?> from, String attribute, JoinType joinType) {
        for (Join<?, ?> join : from.getJoins()) {
            if (join.getAttribute().getName().equals(attribute)) {
                return join;
            }
        }
        return from.join(attribute, joinType);
    }



    /*CriteriaBuilder builder = null;
    CriteriaQuery<TarefaOf> query = builder.createQuery(TarefaOf.class);
    Root<TarefaOf> despesaRoot = query.from(TarefaOf.class);
    List<Predicate> predicateList = new ArrayList<>();
    Join<Despesa, Funcionario> join = despesaRoot.join("funcionario", JoinType.INNER);
        predicateList.add(builder.and(builder.equal(join.get())), builder.sum(despesaRoot.get("valor")));
        query.groupBy(join.get("nome"));
        query.where( predicateList.toArray( new Predicate[predicateList.size()] ) );
        query.select(despesaRoot);
    TypedQuery<Despesa> createQuery = em.createQuery(query);

        return (List<Despesa>) createQuery.getResultList();*/


}

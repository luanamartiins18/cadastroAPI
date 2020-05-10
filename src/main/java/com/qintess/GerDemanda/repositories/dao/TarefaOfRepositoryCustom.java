package com.qintess.GerDemanda.repositories.dao;

import com.qintess.GerDemanda.model.TarefaOf;

import java.util.List;
import java.util.Map;

public interface TarefaOfRepositoryCustom {
    List<TarefaOf> findByFilter(Map<String, String> filter);

    List<TarefaOf> findByFilterGroupSiglaAndReferencia(Map<String, String> filter);

    List<TarefaOf> findByFilterGroupSiglaAndReferenciaAndNumOf(Map<String, String> filter);
}

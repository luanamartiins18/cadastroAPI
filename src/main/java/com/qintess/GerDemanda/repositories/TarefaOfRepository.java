package com.qintess.GerDemanda.repositories;
import com.qintess.GerDemanda.model.TarefaOf;
import com.qintess.GerDemanda.service.dto.RelatorioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaOfRepository extends JpaRepository<TarefaOf, Integer> {
    @Query(" SELECT new com.qintess.GerDemanda.service.dto.RelatorioDTO(" +
            "   orf.numeroOFGenti, " +
            "   usu.nome, " +
            "   st.descricao, " +
            "   SUM(t.itemGuia.valor), " +
            "   orf.referencia, " +
            "   orf.sigla.descricao " +
            " )" +
            " FROM  TarefaOf t " +
            " INNER JOIN t.usuarioOrdemFornecimento u " +
            " INNER JOIN u.usuario usu " +
            " INNER JOIN u.ordemFornecimento orf " +
            " INNER JOIN t.itemGuia ig " +
            " INNER JOIN orf.sigla s " +
            " INNER JOIN t.situacao st " +
            " WHERE  orf.situacaoUsu in (6,8) " +
            " GROUP BY  " +
            "   orf.numeroOFGenti, " +
            "   usu.nome, " +
            "   st.descricao, " +
            "   orf.referencia, " +
            "   orf.sigla.descricao " +
            " ORDER BY " +
            " orf.referencia ")
    List<RelatorioDTO> getRelatorioNovo();


}
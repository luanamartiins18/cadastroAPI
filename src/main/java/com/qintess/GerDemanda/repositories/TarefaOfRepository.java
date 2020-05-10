package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.TarefaOf;
import com.qintess.GerDemanda.service.dto.RelatorioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaOfRepository extends JpaRepository<TarefaOf, Integer>, JpaSpecificationExecutor<TarefaOf> {
    @Query(value = " SELECT " +
            " usu.nome, " +
            " tg.tarefa, " +
            " cg.descricao AS complexidade, " +
            " tg.descricao AS desc_trf, " +
            " count(ig.id) AS qtd_artefatos, " +
            " sum(ig.valor) AS vlr_ustibb " +
            " FROM tarefa_of t " +
            " INNER JOIN usuario_x_of u ON t.fk_of_usuario = u.id " +
            " INNER JOIN  usuario usu ON usu.id = u.fk_usuario " +
            " INNER JOIN ordem_forn orf ON orf.id = u.fk_ordem_forn " +
            " INNER JOIN item_guia ig ON ig.id = t.fk_item_guia " +
            " INNER JOIN complex_guia cg ON cg.id = ig.fk_complex_guia " +
            " INNER JOIN tarefa_guia tg ON tg.id = ig.fk_tarefa_guia " +
            " WHERE u.fk_ordem_forn = :idOf " +
            " GROUP BY " +
            " usu.nome, " +
            " tg.tarefa, " +
            " tg.descricao, " +
            " cg.descricao  " +
            " ORDER BY" +
            " usu.nome,  " +
            " tg.fk_disciplina, " +
            " substring_index(substring_index(tg.tarefa, '.', 2), '.', -1), " +
            " substring_index(tg.tarefa, '.', -1), " +
            " cg.id ", nativeQuery = true)
    List<Object[]> queryRelatorioOrcamento(Integer idOf);

    @Query(value = " SELECT " +
            " usu.nome, " +
            " tg.tarefa, " +
            " tg.descricao AS desc_trf, " +
            " cg.descricao AS complexidade, " +
            " t.num_tarefa, " +
            " t.historia, " +
            " t.sprint, " +
            " ig.valor, " +
            " concat(numero_OF_genti, '/', t.num_tarefa, '/', t.sprint, '/', t.artefato) AS alm, " +
            " t.artefato " +
            " FROM tarefa_of t " +
            " INNER JOIN usuario_x_of u ON t.fk_of_usuario = u.id " +
            " INNER JOIN usuario usu ON usu.id = u.fk_usuario " +
            " INNER JOIN ordem_forn orf ON orf.id = u.fk_ordem_forn " +
            " INNER JOIN item_guia ig ON ig.id = t.fk_item_guia " +
            " INNER JOIN complex_guia cg ON cg.id = ig.fk_complex_guia " +
            " INNER JOIN tarefa_guia tg ON tg.id = ig.fk_tarefa_guia " +
            " WHERE u.fk_ordem_forn = ?1" +
            " ORDER BY " +
            " usu.nome, " +
            " tg.fk_disciplina, " +
            " substring_index(substring_index(tg.tarefa, '.', 2), '.', -1), " +
            " substring_index(tg.tarefa, '.', -1), " +
            " cg.id ", nativeQuery = true)
    List<Object[]> queryRelatorioEntrega(Integer idOf);


    @Query(" SELECT new com.qintess.GerDemanda.service.dto.RelatorioDTO(" +
            "   orf.numeroOFGenti, " +
            "   usu.nome, " +
            "   st.descricao, " +
            "   SUM(t.itemGuia.valor), " +
            "   orf.referencia, " +
            "   orf.sigla.descricao, " +
            "   (" +
            "       SELECT COUNT(so.id) " +
            "       FROM OrdemFornecimento so " +
            "       WHERE " +
            "           so.situacaoUsu.id IN (6,8) " +
            "           AND so.referencia = orf.referencia " +
            "           AND so.sigla.id =  s.id " +
            "   ) " +
            " )" +
            " FROM  TarefaOf t " +
            " INNER JOIN t.usuarioOrdemFornecimento u " +
            " INNER JOIN u.usuario usu " +
            " INNER JOIN u.ordemFornecimento orf " +
            " INNER JOIN t.itemGuia ig " +
            " INNER JOIN orf.sigla s " +
            " INNER JOIN orf.situacaoUsu st " +
            " WHERE  st.id IN (6,8) " +
            " GROUP BY  " +
            "   s.descricao, " +
            "   orf.referencia " +
            " ORDER BY " +
            " orf.referencia ")
    List<RelatorioDTO> getRelatorioSiglaReferenciaReduzido();

    @Query(" SELECT new com.qintess.GerDemanda.service.dto.RelatorioDTO(" +
            "   orf.numeroOFGenti, " +
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
            " INNER JOIN orf.situacaoUsu st " +
            " WHERE  st.id IN (6,8) " +
            " GROUP BY  " +
            "   orf.numeroOFGenti, " +
            "   st.descricao, " +
            "   orf.referencia, " +
            "   orf.sigla.descricao " +
            " ORDER BY " +
            " s.descricao, orf.referencia  ")
    List<RelatorioDTO> getRelatorioSiglaReferenciaExpandido();

    @Query( " SELECT new com.qintess.GerDemanda.service.dto.RelatorioDTO(" +
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
            " INNER JOIN orf.situacaoUsu st " +
            " WHERE  st.id IN (6,8) " +
            " GROUP BY  " +
            "   orf.numeroOFGenti, " +
            "   usu.nome, " +
            "   st.descricao, " +
            "   orf.referencia, " +
            "   orf.sigla.descricao " +
            " ORDER BY " +
            " s.descricao, orf.referencia  ")
    List<RelatorioDTO> getRelatorioSiglaReferencia();

    List<TarefaOf> findByUsuarioOrdemFornecimentoUsuarioIdAndUsuarioOrdemFornecimentoOrdemFornecimentoId(Integer idUsu, Integer idOf);

    List<TarefaOf> findByUsuarioOrdemFornecimentoOrdemFornecimentoId(Integer idOf);
}
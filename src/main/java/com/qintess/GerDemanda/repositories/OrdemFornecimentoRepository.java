package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdemFornecimentoRepository extends JpaRepository<OrdemFornecimento, Integer> {

    @Query(value = " SELECT orf.id, orf.numero_OF_genti, orf.referencia, orf.responsavel_t, orf.gerente_t, orf.dt_abertura, " +
            " orf.dt_previsao, orf.dt_entrega, orf.dt_aceite, s.descricao sigla, sit.descricao sit_genti, st.descricao sit_alm, " +
            " sum( CASE WHEN (t.fk_situacao = 4 OR t.fk_situacao = 8) THEN ig.valor ELSE 0 END ) AS valorExecutado, " +
            " sum( CASE WHEN (t.fk_situacao != 2 AND t.fk_situacao != 5) THEN ig.valor ELSE 0 END ) AS valorPlanejado " +
            " FROM ordem_forn orf " +
            " INNER JOIN sigla s ON s.id = orf.fk_sigla " +
            " INNER JOIN situacao sit ON orf.fk_situacao_genti = sit.id " +
            " LEFT JOIN situacao st ON orf.fk_situacao_usu = st.id " +
            " LEFT JOIN usuario_x_of uof ON uof.fk_ordem_forn = orf.id " +
            " LEFT JOIN tarefa_of t ON t.fk_of_usuario = uof.id " +
            " LEFT JOIN item_guia ig ON ig.id = t.fk_item_guia " +
            " WHERE orf.fk_situacao_genti = 6 AND (uof.status = 1 OR uof.status IS NULL) " +
            " GROUP BY orf.id, orf.numero_OF_genti, orf.referencia, orf.responsavel_t, orf.gerente_t, orf.dt_abertura, " +
            " orf.dt_previsao, orf.dt_entrega, orf.dt_aceite, s.descricao, sit.descricao, st.descricao " +
            " ORDER BY st.descricao, s.descricao ", nativeQuery = true)
    List<Object[]> getOrdemDeFornecimento();

    OrdemFornecimento findFirstByIdAndSituacaoGentiId(Integer id, Integer i);

    @Query(value = " SELECT " +
            " orf.referencia, " +
            " orf.numero_OF_genti num_of, " +
            " s.descricao AS sigla, " +
            " orf.responsavel_t, " +
            " orf.gerente_t, " +
            " SUM(ig.valor) AS utsibb, " +
            " ((SELECT valor FROM valor_ustibb WHERE ativo = 1) * SUM(ig.valor)) AS valor, " +
            " st.descricao AS status, " +
            " orf.id AS idOrf " +
            " FROM ordem_forn orf " +
            " INNER JOIN usuario_x_of uof ON uof.fk_ordem_forn = orf.id " +
            " INNER JOIN tarefa_of tof ON tof.fk_of_usuario = uof.id " +
            " INNER JOIN item_guia ig ON tof.fk_item_guia = ig.id " +
            " INNER JOIN sigla s ON orf.fk_sigla = s.id " +
            " INNER JOIN situacao st ON orf.fk_situacao_usu = st.id " +
            " WHERE orf.fk_situacao_usu = 8 " +
            " GROUP BY " +
            " orf.numero_OF_genti, " +
            " orf.responsavel_t, " +
            " orf.gerente_t, " +
            " orf.referencia, " +
            " s.descricao ", nativeQuery = true)
    List<Object[]> findOrdensConcluidas();
}
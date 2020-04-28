package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.TarefaGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaGuiaRepository extends JpaRepository<TarefaGuia, Integer> {

    @Query(value = " SELECT tg.*, um.descricao AS desc_uni_med, ig.id AS id_item, ig.limite_itens, ig.componente, cg.descricao AS desc_complex, cg.id AS id_complex, ig.descricao_complex, ig.valor " +
            " FROM tarefa_guia tg " +
            " INNER JOIN item_guia ig ON ig.fk_tarefa_guia = tg.id " +
            " INNER JOIN uni_medida um ON um.id = ig.fk_uni_medida " +
            " INNER JOIN complex_guia cg ON cg.id = ig.fk_complex_guia " +
            " ORDER BY " +
            " tg.fk_disciplina, " +
            " cast(substring_index( substring_index(tg.atividade, ' ', 1), '.', -1 ) AS UNSIGNED), " +
            " cast(substring_index(tg.tarefa, '.', -1) AS UNSIGNED) ", nativeQuery = true)
    List<Object[]> getItensGuia();

    @Query(value = " SELECT DISTINCT atividade, fk_disciplina " +
            " FROM tarefa_guia " +
            " ORDER BY fk_disciplina, cast(substring_index( substring_index(atividade, ' ', 1), '.', -1 ) AS UNSIGNED) ", nativeQuery = true)
    List<Object[]> getAtividades();

    @Query(value = " SELECT DISTINCT atividade " +
            " FROM tarefa_guia " +
            " ORDER BY fk_disciplina, cast(substring_index( substring_index(atividade, ' ', 1), '.', -1 ) AS UNSIGNED) ", nativeQuery = true)
    List<String> atividadeJaExiste();

    @Query(value = " SELECT DISTINCT atividade " +
            " FROM tarefa_guia " +
            " WHERE fk_disciplina = ?1 " +
            " ORDER BY fk_disciplina, cast(substring_index( substring_index(atividade, ' ', 1), '.', -1 ) AS UNSIGNED) ", nativeQuery = true)
    List<String> criaNumeroAtividade(Integer idDisciplina);

    @Query(value = " SELECT DISTINCT tarefa " +
            " FROM tarefa_guia " +
            " WHERE fk_disciplina = ?1 AND tarefa LIKE (?2) " +
            " ORDER BY fk_disciplina, cast(substring_index( substring_index(atividade, ' ', 1), '.', -1 ) AS UNSIGNED) ", nativeQuery = true)
    List<String> criaNumeroTarefa(Integer idDisciplina, String atividade);

}
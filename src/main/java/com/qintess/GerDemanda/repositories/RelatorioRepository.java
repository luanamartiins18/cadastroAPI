package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<OrdemFornecimento, Integer> {

    @Query(value = " select " +
            " orf.numero_OF_genti as numero_of, " +
            "        usu.nome as colaborador, " +
            "        st.descricao as status_of, " +
            " sum(ig.valor) as valor_ustibb, " +
            "        ((select valor from valor_ustibb where ativo = 1) * SUM(ig.valor)) as valor, " +
            " orf.referencia, " +
            " s.descricao as sigla, " +
            " orf.id as idOrf " +
            " from " +
            " tarefa_of t" +
            " inner join usuario_x_of u " +
            " on t.fk_of_usuario = u.id " +
            " inner join usuario usu " +
            " on usu.id = u.fk_usuario " +
            " inner join  ordem_forn orf " +
            " on orf.id = u.fk_ordem_forn " +
            " inner join item_guia ig " +
            " on ig.id = t.fk_item_guia " +
            " inner join sigla s " +
            " on orf.fk_sigla = s.id " +
            " inner join situacao st " +
            " on orf.fk_situacao_usu = st.id " +
            "    where " +
            " orf.fk_situacao_usu in (6,8) " +
            " group by " +
            " usu.nome, " +
            " orf.numero_OF_genti, " +
            " orf.referencia, " +
            " s.descricao " +
            " order by " +
            " orf.referencia; ", nativeQuery = true)

    List<Object[]> getRelatorioNovo();

}
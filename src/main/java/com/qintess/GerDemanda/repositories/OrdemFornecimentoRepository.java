package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.model.Sigla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdemFornecimentoRepository extends JpaRepository<OrdemFornecimento, Integer> {

    @Query(value = "select orf.id, orf.numero_OF_genti, orf.referencia, orf.responsavel_t, orf.gerente_t, orf.dt_abertura, " +
            "		orf.dt_previsao, orf.dt_entrega, orf.dt_aceite, s.descricao sigla, sit.descricao sit_genti, st.descricao sit_alm, " +
            "		sum( " +
            "			case  " +
            "				when (t.fk_situacao = 4 or t.fk_situacao = 8) " +
            "				then ig.valor " +
            "                else 0 " +
            "                end " +
            "        ) as valorExecutado, " +
            "		sum( " +
            "			case  " +
            "				when (t.fk_situacao != 2 and t.fk_situacao != 5) " +
            "				then ig.valor " +
            "                else 0 " +
            "                end " +
            "        ) as valorPlanejado " +
            "from ordem_forn orf " +
            "inner join sigla s " +
            "	on s.id = orf.fk_sigla " +
            "inner join situacao sit " +
            "	on orf.fk_situacao_genti = sit.id " +
            "left join situacao st " +
            "	on orf.fk_situacao_usu = st.id     " +
            "left join usuario_x_of uof " +
            "	on uof.fk_ordem_forn = orf.id " +
            "left join tarefa_of t " +
            "	on t.fk_of_usuario = uof.id " +
            "left join item_guia ig " +
            "	on ig.id = t.fk_item_guia " +
            "where orf.fk_situacao_genti = 6  and (uof.status = 1 or uof.status is null) " +
            "group by orf.id, orf.numero_OF_genti, orf.referencia, orf.responsavel_t, orf.gerente_t, orf.dt_abertura, " +
            "		orf.dt_previsao, orf.dt_entrega, orf.dt_aceite, s.descricao, sit.descricao, st.descricao "
            + " order by st.descricao, s.descricao")
    List<Object[]> getOrdemDeFornecimento();

}
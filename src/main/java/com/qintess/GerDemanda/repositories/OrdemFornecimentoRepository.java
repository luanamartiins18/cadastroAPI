package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdemFornecimentoRepository extends JpaRepository<OrdemFornecimento, Integer> {

    @Query(value = "SELECT " +
            "	orf.referencia, " +
            "	orf.numero_OF_genti num_of, " +
            "	s.descricao as sigla, " +
            "    orf.responsavel_t, " +
            "    orf.gerente_t, " +
            "    SUM(ig.valor) as utsibb, " +
            "    ((select valor from valor_ustibb where ativo = 1) * SUM(ig.valor)) as valor, " +
            "    st.descricao as status,         " +
            "    orf.id as idOrf   "  +
            "	FROM ordem_forn orf " +
            "		INNER JOIN usuario_x_of uof " +
            "			on uof.fk_ordem_forn = orf.id  " +
            "		INNER JOIN tarefa_of tof " +
            "			on tof.fk_of_usuario = uof.id " +
            "        INNER JOIN item_guia ig " +
            "			on tof.fk_item_guia = ig.id " +
            "		INNER JOIN sigla s " +
            "			on orf.fk_sigla = s.id " +
            "		INNER JOIN situacao st " +
            "			on orf.fk_situacao_usu = st.id " +
            "    WHERE  " +
            "		orf.fk_situacao_usu = 8 " +
            "	GROUP BY " +
            "    	orf.numero_OF_genti, " +
            "		orf.responsavel_t, " +
            "		orf.gerente_t, " +
            "		orf.referencia, " +
            "		s.descricao ", nativeQuery = true)

    List<Object[]> findOrdensConcluidas();

}

package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.HistoricoOperacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoricoOperacaoRepository extends JpaRepository<HistoricoOperacao, Integer> {



      @Query(
             value = "SELECT * FROM historico_operacao WHERE FK_USUARIO  = ?1 ORDER BY data_inicio  DESC",
             nativeQuery = true
      )
     List<HistoricoOperacao> findByByOperacaoOrderByDataInicioDesc(Integer id);

      @Query(
            value = "SELECT * FROM historico_operacao WHERE FK_USUARIO = ?1 ORDER BY data_inicio DESC LIMIT 1",
            nativeQuery = true
      )
      HistoricoOperacao findUltimoHistoricoByOperacao (Integer id);

      @Modifying
      @Query(
              value = "UPDATE historico_operacao SET data_final = ?1, vigente = ?2 WHERE id = ?3",
              nativeQuery = true
      )
      void updateUltimoHistoricoOperacao(Date data_final,String vigente,Integer id);


      @Query(
              value = "SELECT * FROM historico_operacao ORDER BY data_inicio DESC",
              nativeQuery = true
      )
      List<HistoricoOperacao> findAllByOrderByDataInicioDesc();

      List<HistoricoOperacao> findAll();

}

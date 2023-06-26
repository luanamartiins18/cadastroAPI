package com.qintess.GerDemanda.service.repositories;

import com.qintess.GerDemanda.model.HistoricoProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoricoPropostaRepository extends JpaRepository<HistoricoProposta, Integer>  {

    @Query(
            value = "SELECT * FROM historico_proposta WHERE FK_proposta  = ?1 ORDER BY data_inicio  DESC",
            nativeQuery = true

    )
    List<HistoricoProposta> findByPropostaOrderByDataInicioDesc(Integer id);


    @Query(
            value = "SELECT * FROM historico_proposta WHERE FK_PROPOSTA = ?1 ORDER BY data_inicio DESC LIMIT 1",
            nativeQuery = true
    )
    HistoricoProposta findUltimoHistoricoByProposta(Integer id);

    @Modifying
    @Query(
            value = "UPDATE historico_proposta SET data_final = ?1, vigente = ?2 WHERE id = ?3",
            nativeQuery = true
    )
    void updateUltimoHistoricoProposta(Date data_final, String vigente, Integer id);


    List<HistoricoProposta> findAll();

    @Query(
            value = "SELECT * FROM historico_proposta ORDER BY data_inicio DESC",
            nativeQuery = true
    )
    List<HistoricoProposta> findAllByOrderByDataInicioDesc();

    @Query(
            value = "SELECT * FROM historico_proposta WHERE Fk_CANDIDATO = ?1 ORDER BY data_inicio DESC",
            nativeQuery = true
    )
    List<HistoricoProposta> listaHistoricoPropostaPorCandidato(Integer idCandidato);


}


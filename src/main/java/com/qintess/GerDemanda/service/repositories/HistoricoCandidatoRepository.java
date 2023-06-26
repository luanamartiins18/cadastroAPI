package com.qintess.GerDemanda.service.repositories;


import com.qintess.GerDemanda.model.HistoricoCandidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoricoCandidatoRepository extends JpaRepository<HistoricoCandidato, Integer> {

    @Query(
            value = "SELECT * FROM historico_candidato WHERE FK_CANDIDATO = ?1 ORDER BY data_inicio DESC LIMIT 1",
            nativeQuery = true
    )
    HistoricoCandidato findUltimoHistoricoByCandidato(Integer id);


    @Query(
            value = "SELECT * FROM historico_candidato WHERE FK_Candidato  = ?1 ORDER BY data_inicio  DESC",
            nativeQuery = true
    )
    List<HistoricoCandidato> findByCandiatoOrderByDataInicioDesc(Integer id);

    @Modifying
    @Query(
            value = "UPDATE historico_candidato SET data_final = ?1, vigente = ?2 WHERE id = ?3",
            nativeQuery = true
    )
    void updateUltimoHistoricoCandidato(Date data_final, String vigente, Integer id);




}

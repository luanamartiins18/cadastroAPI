package com.qintess.GerDemanda.service.mapper.repositories;

import com.qintess.GerDemanda.model.HistoricoMaquinas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoricoMaquinasRepository extends JpaRepository<HistoricoMaquinas, Integer> {



        @Query(
                value = "SELECT * FROM historico_maquinas WHERE FK_USUARIO  = ?1 ORDER BY data_inicio  DESC",
                nativeQuery = true
        )
        List<HistoricoMaquinas> findByByMaquinasOrderByDataInicioDesc(Integer id);

        @Query(
                value = "SELECT * FROM historico_maquinas WHERE FK_USUARIO = ?1 ORDER BY data_inicio DESC LIMIT 1",
                nativeQuery = true
        )
        HistoricoMaquinas findUltimoHistoricoByMaquinas (Integer id);

        @Modifying
        @Query(
                value = "UPDATE historico_maquinas SET data_inicio = ?1, data_final = ?2, vigente = ?3 WHERE id = ?4",
                nativeQuery = true
        )
        void updateUltimoHistoricoMaquinas(Date data_inicio, Date data_final, String vigente, Integer id);


        @Query(
                value = "SELECT * FROM historico_maquinas ORDER BY data_inicio DESC",
                nativeQuery = true
        )
        List<HistoricoMaquinas> findAllByOrderByDataInicioDesc();

        List<HistoricoMaquinas> findAll();

}


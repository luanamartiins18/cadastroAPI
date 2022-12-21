package com.qintess.GerDemanda.service.mapper.repositories;


import com.qintess.GerDemanda.model.HistoricoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoUsuario, Integer> {


    @Query(
            value = "SELECT * FROM historico_usuario WHERE FK_USUARIO  = ?1 ORDER BY data_inicio  DESC",
            nativeQuery = true

    )
    List<HistoricoUsuario> findByByUsuarioOrderByDataInicioDesc(Integer id);

    @Query(
            value = "SELECT * FROM historico_usuario WHERE FK_USUARIO = ?1 ORDER BY data_inicio DESC LIMIT 1",
            nativeQuery = true
    )
    HistoricoUsuario findUltimoHistoricoByUsuario (Integer id);

    @Modifying
    @Query(
            value = "UPDATE historico_usuario SET data_final = ?1, vigente = ?2 WHERE id = ?3",
            nativeQuery = true
    )
    void updateUltimoHistorico(Date data_final,String vigente,Integer id);


    List<HistoricoUsuario> findAll();

    @Query(
            value = "SELECT * FROM historico_usuario ORDER BY data_inicio DESC",
            nativeQuery = true
    )
    List<HistoricoUsuario> findAllByOrderByDataInicioDesc();

    @Query(
            value = "DELETE FROM historico_usuario",
            nativeQuery = true
    )
    void deleteByHistoricoId(Integer id);

}

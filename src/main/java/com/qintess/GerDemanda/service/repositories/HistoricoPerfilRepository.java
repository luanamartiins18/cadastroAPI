package com.qintess.GerDemanda.service.repositories;

import com.qintess.GerDemanda.model.HistoricoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoricoPerfilRepository extends JpaRepository<HistoricoPerfil, Integer> {


    @Query(
            value = "SELECT * FROM historico_perfil WHERE FK_USUARIO  = ?1 ORDER BY data_inicio  DESC",
            nativeQuery = true

    )
    List<HistoricoPerfil> findByByUsuarioOrderByDataInicioDesc(Integer id);

    @Query(
            value = "SELECT * FROM historico_perfil WHERE FK_USUARIO = ?1 ORDER BY data_inicio DESC LIMIT 1",
            nativeQuery = true
    )
    HistoricoPerfil findUltimoHistoricoByUsuario(Integer id);

    @Modifying
    @Query(
            value = "UPDATE historico_perfil SET data_final = ?1, vigente = ?2 WHERE id = ?3",
            nativeQuery = true
    )
    void updateUltimoHistoricoPerfil(Date data_final,String vigente,Integer id);


    List<HistoricoPerfil> findAll();

    @Query(
            value = "SELECT * FROM historico_perfil ORDER BY data_inicio DESC",
            nativeQuery = true
    )
    List<HistoricoPerfil> findAllByOrderByDataInicioDesc();

    @Query(
            value = "DELETE FROM historico_perfil",
            nativeQuery = true
    )
    void deleteByHistoricoId(Integer id);

}

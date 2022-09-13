package com.qintess.GerDemanda.repositories;


import com.qintess.GerDemanda.model.HistoricoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface HistoricoRepository extends JpaRepository<HistoricoUsuario, Integer> {


    @Query(
            value = "SELECT * FROM HISTORICO_USUARIO WHERE FK_USUARIO = ?1",
            nativeQuery = true
    )
    HistoricoUsuario findByUsuario (Integer id);

    List<HistoricoUsuario>findAll();
}

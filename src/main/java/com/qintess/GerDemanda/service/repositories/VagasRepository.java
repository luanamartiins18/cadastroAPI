package com.qintess.GerDemanda.service.repositories;

import com.qintess.GerDemanda.model.Vagas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VagasRepository extends JpaRepository<Vagas, Integer> {


    @Query(
            value = "SELECT * FROM vagas WHERE fk_etapa = ?1",
            nativeQuery = true
    )
    List<Vagas> listarVagasPorEtapa(Integer idEtapa);

    List<Vagas> findByOrderByCargoAsc();

    List<Vagas> findByOrderByIdAsc();

    Vagas findFirstByQualitor(String nr);

    Vagas findFirstByQualitorAndIdNot (String qualitor, Integer id);
}

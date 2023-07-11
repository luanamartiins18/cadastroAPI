package com.qintess.GerDemanda.service.repositories;

import com.qintess.GerDemanda.model.Candidatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CandidatosRepository extends JpaRepository<Candidatos, Integer> {


    boolean existsByCpf(String cpf);

    @Query(
            value = "SELECT * FROM candidatos WHERE fk_vagas = ?1",
            nativeQuery = true
    )
    List<Candidatos> listarCandidatosPorVaga(Integer id);


    @Query(
            value = "SELECT * FROM candidatos WHERE fk_status_candidato = ?1",
            nativeQuery = true
    )
    List<Candidatos> listarCandidatoPorStatus(Integer idStatus);


    @Query(
            value = "SELECT * FROM candidatos WHERE fk_status_candidato = 6",
            nativeQuery = true
    )
    List<Candidatos> listarCandidatoDisponivel();

    List<Candidatos> findByOrderByCandidatosAsc();

    Candidatos findFirstByCpfAndIdNot(String cpf, Integer id);

    Candidatos  findFirstByEmailAndIdNot(String email, Integer id);

    Candidatos findFirstById(Integer id);

    Candidatos findFirstByTelefoneAndIdNot(String telefone, Integer id);


}

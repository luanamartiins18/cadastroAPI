package com.qintess.GerDemanda.service.mapper.repositories;

import com.qintess.GerDemanda.model.StatusCandidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusCandidatoRepository extends JpaRepository<StatusCandidato, Integer>{

    List<StatusCandidato> findByOrderByDescricaoAsc();
}

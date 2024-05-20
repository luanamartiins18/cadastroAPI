package com.qintess.GerDemanda.service.repositories;

import com.qintess.GerDemanda.model.StatusCandidato;
import com.qintess.GerDemanda.model.StatusMaquina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusMaquinaRepository extends JpaRepository<StatusMaquina, Integer>{

    List<StatusMaquina> findByOrderByDescricaoAsc();
}

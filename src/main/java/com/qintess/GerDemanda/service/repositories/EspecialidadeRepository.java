package com.qintess.GerDemanda.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qintess.GerDemanda.model.Especialidade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadeRepository  extends JpaRepository<Especialidade, Integer> {
    List<Especialidade> findByOrderByDescricaoAsc();
}

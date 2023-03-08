package com.qintess.GerDemanda.service.mapper.repositories;

import com.qintess.GerDemanda.model.PlanoSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanoSaudeRepository extends JpaRepository<PlanoSaude, Integer> {

    List<PlanoSaude> findByOrderByDescricaoAsc();
}

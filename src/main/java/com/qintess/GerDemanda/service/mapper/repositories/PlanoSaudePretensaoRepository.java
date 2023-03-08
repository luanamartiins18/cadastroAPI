package com.qintess.GerDemanda.service.mapper.repositories;

import com.qintess.GerDemanda.model.PlanoSaudePretensao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanoSaudePretensaoRepository extends JpaRepository<PlanoSaudePretensao, Integer> {

    List<PlanoSaudePretensao> findByOrderByDescricaoAsc();
}

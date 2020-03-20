package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Sigla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiglaRepository extends JpaRepository<Sigla, Integer> {
    List<Sigla> findByOrderByDescricaoAsc();
}
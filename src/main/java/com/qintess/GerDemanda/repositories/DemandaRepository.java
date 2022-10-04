package com.qintess.GerDemanda.repositories;


import com.qintess.GerDemanda.model.Demanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandaRepository extends JpaRepository<Demanda, Integer> {

     List<Demanda> findByOrderByDescricaoAsc();
}
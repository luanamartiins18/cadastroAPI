package com.qintess.GerDemanda.service.repositories;


import com.qintess.GerDemanda.model.CentroCusto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentroCustoRepository extends JpaRepository<CentroCusto, Integer> {

List<CentroCusto> findByOrderByDescricaoAsc();
}
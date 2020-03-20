package com.qintess.GerDemanda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemFornecimentoRepository extends JpaRepository<OrdemFornecimentoRepository, Integer> {

}
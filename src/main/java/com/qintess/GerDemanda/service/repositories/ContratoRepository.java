package com.qintess.GerDemanda.service.repositories;

import com.qintess.GerDemanda.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {

    List<Contrato> findByOrderByOperacaoAsc();
}


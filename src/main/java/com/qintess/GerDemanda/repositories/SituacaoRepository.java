package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SituacaoRepository extends JpaRepository<Situacao, Integer> {

    List<Situacao> findByOrderByDescricaoAsc();
}
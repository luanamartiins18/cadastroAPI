package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.TarefaGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaGuiaRepository extends JpaRepository<TarefaGuia, Integer> {
}
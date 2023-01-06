package com.qintess.GerDemanda.service.mapper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qintess.GerDemanda.model.Recrutador;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecrutadorRepository extends JpaRepository <Recrutador, Integer> {

    List<Recrutador> findByOrderByDescricaoAsc();
}

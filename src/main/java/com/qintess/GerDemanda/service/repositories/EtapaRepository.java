package com.qintess.GerDemanda.service.repositories;



import com.qintess.GerDemanda.model.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Integer>{

    List<Etapa> findByOrderByDescricaoAsc();
}

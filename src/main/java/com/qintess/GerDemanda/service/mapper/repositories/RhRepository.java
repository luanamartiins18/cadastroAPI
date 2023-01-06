package com.qintess.GerDemanda.service.mapper.repositories;


import com.qintess.GerDemanda.model.Rh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RhRepository extends JpaRepository<Rh, Integer> {

    List<Rh> findByOrderByCandidatoAsc();
}

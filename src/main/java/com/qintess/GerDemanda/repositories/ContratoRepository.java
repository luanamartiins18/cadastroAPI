package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository extends JpaRepository<Cargo, Integer> {

}
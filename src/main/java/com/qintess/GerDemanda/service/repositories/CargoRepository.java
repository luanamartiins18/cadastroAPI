package com.qintess.GerDemanda.service.repositories;

import com.qintess.GerDemanda.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    List<Cargo> findByOrderByDescricaoAsc();
}
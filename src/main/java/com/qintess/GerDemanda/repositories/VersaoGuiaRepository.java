package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.VersaoGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersaoGuiaRepository extends JpaRepository<VersaoGuia, Integer> {
}
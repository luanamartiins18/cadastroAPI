package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.ComplexGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexGuiaRepository extends JpaRepository<ComplexGuia, Integer> {

}
package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.UniMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniMedidaRepository extends JpaRepository<UniMedida, Integer> {

}
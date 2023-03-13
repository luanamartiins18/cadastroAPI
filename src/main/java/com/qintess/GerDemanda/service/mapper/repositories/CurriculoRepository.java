package com.qintess.GerDemanda.service.mapper.repositories;

import com.qintess.GerDemanda.model.Curriculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CurriculoRepository extends JpaRepository<Curriculo, Integer> {

}

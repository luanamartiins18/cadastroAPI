package com.qintess.GerDemanda.service.mapper.repositories;

import com.qintess.GerDemanda.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface EquipamentoRepository  extends JpaRepository<Equipamento, Integer>  {

    List<Equipamento> findByOrderByDescricaoAsc();
}
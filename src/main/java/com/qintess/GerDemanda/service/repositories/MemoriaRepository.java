package com.qintess.GerDemanda.service.repositories;


import com.qintess.GerDemanda.model.Memoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface MemoriaRepository  extends JpaRepository<Memoria, Integer>  {

    List<Memoria> findByOrderByDescricaoAsc();
}
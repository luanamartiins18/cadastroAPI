package com.qintess.GerDemanda.repositories;



import com.qintess.GerDemanda.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Integer> {

    List<Tipo> findByOrderByDescricaoAsc();
}

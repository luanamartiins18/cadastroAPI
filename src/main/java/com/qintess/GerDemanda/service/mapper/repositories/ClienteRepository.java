package com.qintess.GerDemanda.service.mapper.repositories;


import com.qintess.GerDemanda.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByOrderByDescricaoAsc();
}
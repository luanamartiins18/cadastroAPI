package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.UsuarioSigla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioSiglaRepository extends JpaRepository<UsuarioSigla, Integer> {
    void deleteByUsuarioSiglaId(Integer id);
}
package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Perfil;
import com.qintess.GerDemanda.model.Sigla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    List<Perfil> findByOrderByDescricaoAsc();
}
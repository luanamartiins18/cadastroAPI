package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioOrdemFornecimentoRepository extends JpaRepository<UsuarioOrdemFornecimento, Integer> {

}
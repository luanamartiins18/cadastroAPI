package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
    List<Mensagem> findByOrderByDtExpiracaoAsc();
}
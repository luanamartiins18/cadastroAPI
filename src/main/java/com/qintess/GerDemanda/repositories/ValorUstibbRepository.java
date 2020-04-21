package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.ValorUstibb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorUstibbRepository extends JpaRepository<ValorUstibb, Integer> {
    ValorUstibb findByAtivo(int i);
}
package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.UsuarioMensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioMensagemRepository extends JpaRepository<UsuarioMensagem, Integer> {

}
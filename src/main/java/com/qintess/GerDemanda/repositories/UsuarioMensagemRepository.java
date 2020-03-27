package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.UsuarioMensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UsuarioMensagemRepository extends JpaRepository<UsuarioMensagem, Integer> {
    List<UsuarioMensagem> findByMensagemStatusAndUsuarioMensId(int status, int idUsuario);
    List<UsuarioMensagem> findByMensagemId(Integer idMensagem);
    List<UsuarioMensagem> findByDtLeituraIsNullAndMensagemDtExpiracaoGreaterThanEqualAndMensagemStatusAndUsuarioMensId(Date date, Integer status, Integer usuarioMensId);
}
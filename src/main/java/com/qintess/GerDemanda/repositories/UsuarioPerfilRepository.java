package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.UsuarioPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Integer> {
    UsuarioPerfil findByUsuarioPerfilIdAndStatus(int idUsu, int statusAtivoCodigo);
}
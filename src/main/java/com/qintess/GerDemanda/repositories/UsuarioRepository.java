package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Sigla;
import com.qintess.GerDemanda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCodigoReAndSenha(String re, String senha);
    Usuario findFirstByCodigoRe(String re);

    List<Usuario> findByStatusAndCargoIdOrderByNomeAsc(String ativo, int i);

    List<Usuario> findByStatusAndCargoIdAndListaSiglasSiglaIdOrderByNomeAsc(String statusAtivo, int cargoColaborador, Integer Sigla);
}
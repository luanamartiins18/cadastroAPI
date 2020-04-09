package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByCodigoReAndSenha(String re, String senha);

    Usuario findFirstByCodigoRe(String re);

    Usuario findFirstByCodigoBB(String bb);

    Usuario findFirstByEmail(String email);

    Usuario findFirstByCpf(String cpf);

    List<Usuario> findByStatusAndCargoIdOrderByNomeAsc(String ativo, Integer i);

    List<Usuario> findByStatusAndCargoIdAndListaSiglasSiglaIdOrderByNomeAsc(String statusAtivo, Integer cargoColaborador, Integer Sigla);

    List<Usuario> findByOrderByNomeAsc();
}
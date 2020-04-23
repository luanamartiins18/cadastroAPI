package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByCodigoReAndSenha(String re, String senha);

    Usuario findFirstByCodigoBBAndIdNot(String bb, Integer id);

    Usuario findFirstByEmailAndIdNot(String email, Integer id);

    Usuario findFirstByCpfAndIdNot(String cpf, Integer id);

    List<Usuario> findByStatusAndCargoIdOrderByNomeAsc(String ativo, Integer i);

    List<Usuario> findByStatusAndCargoIdAndListaSiglasSiglaIdOrderByNomeAsc(String statusAtivo, Integer cargoColaborador, Integer Sigla);

    List<Usuario> findByOrderByNomeAsc();

    Usuario findFirstByCodigoReAndIdNot(String re, Integer id);

    Usuario findFirstByCodigoRe(String re);

}
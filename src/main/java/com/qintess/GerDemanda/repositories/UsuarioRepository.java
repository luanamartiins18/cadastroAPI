package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCodigoReAndSenha(String re, String senha);
    List<Usuario> findByCodigoRe(String re);

    List<Usuario> findByStatusAndCargoIdOrderByNomeAsc(String ativo, int i);

    @Query("select distinct usu from "
            + " Usuario usu "
            + "inner join fetch usu.listaSiglas lis "
            + "inner join fetch usu.listaPerfil lp "
            + "where usu.status = ?1 "
            + "and usu.cargo.id = ?2 "
            + "and lis.sigla.id = ?3 "
            + "order by usu.nome ")
    List<Usuario> findByStatusAndCargoIdAndListaSiglasIdInOrderByNomeAsc(String statusAtivo, int cargoColaborador, int idSigla);

    Usuario findByIdAndStatus(int idUsu, int statusAtivoCodigo);
}
package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import  com.qintess.GerDemanda.service.dto.*;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query(
            value = "UPDATE usuario SET fk_cargo = ?1, fk_bu = ?2, fk_tipo = ?3 WHERE id = ?4",
            nativeQuery = true
    )
     void updateFuncao(CargoDTO idCargo, BuDTO idBu, TipoDTO idTipo, Integer idUsuario);

    Usuario findFirstByEmailAndIdNot(String email, Integer id);

    Usuario findFirstByCpfAndIdNot(String cpf, Integer id);

    List<Usuario> findByStatusAndCargoIdOrderByNomeAsc(String ativo, Integer i);

    List<Usuario> findByOrderByNomeAsc();

    Usuario findFirstByCodigoReAndIdNot(String re, Integer id);

    Usuario findFirstByCodigoRe(String re);

}
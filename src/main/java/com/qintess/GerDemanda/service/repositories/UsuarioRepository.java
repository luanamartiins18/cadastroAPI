package com.qintess.GerDemanda.service.repositories;


import com.qintess.GerDemanda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByCodigoReAndSenha(String re, String senha);

    @Modifying
    @Query(
            value = "UPDATE usuario SET fk_cargo = ?1, fk_bu = ?2, fk_tipo = ?3 WHERE id = ?4",
            nativeQuery = true
    )
     void updateFuncao(Integer idCargo, Integer idBu, Integer idTipo, Integer idUsuario);

    @Modifying
    @Query(
            value = "UPDATE usuario SET fk_perfil = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    void updatePerfil(Integer idPerfil, Integer idUsuario);


    @Modifying
    @Query(
            value = "UPDATE usuario SET fk_contrato = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    void updateContrato(Integer idContrato, Integer idUsuario);

    @Modifying
    @Query(
            value = "UPDATE usuario SET fk_modelo = ?1, fk_equipamento = ?2, fk_memoria = ?3, tag = ?4, patrimonio = ?5 WHERE id = ?6",
            nativeQuery = true
    )
    void updateMaquinas(Integer idModelo, Integer idEquipamento, Integer idMemoria, String Patrimonio, String tag,  Integer idUsuario);


    @Query(
            value = "SELECT * FROM usuario WHERE fk_contrato = ?1",
            nativeQuery = true
    )
    List<Usuario> listarUsuarioPorOperacao(Integer idContrato);


    Usuario findFirstByEmailAndIdNot(String email, Integer id);

    Usuario findFirstByCpfAndIdNot(String cpf, Integer id);

    Usuario findByCpf(String cpf);

    List<Usuario> findByStatusAndCargoIdOrderByNomeAsc(String ativo, Integer i);

    List<Usuario> findByOrderByNomeAsc();

    Usuario findFirstByCodigoReAndIdNot(String re, Integer id);

    Usuario findFirstByCodigoRe(String re);

    Usuario findFirstByCelularAndIdNot(String celular, Integer id);

}
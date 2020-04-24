package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioOrdemFornecimentoRepository extends JpaRepository<UsuarioOrdemFornecimento, Integer> {

    List<UsuarioOrdemFornecimento> findByStatusAndOrdemFornecimentoId(Integer usuarioStatusAtivo, Integer idOf);

    List<UsuarioOrdemFornecimento> findByOrdemFornecimentoSituacaoUsuIdAndUsuarioIdAndDtExclusaoIsNullAndStatusOrderByOrdemFornecimentoSiglaDescricaoAsc(Integer situcaoEmExecucao, Integer id, Integer statusAtivo);

    UsuarioOrdemFornecimento findFirstByStatusAndUsuarioIdAndOrdemFornecimentoId(Integer statusAtivo, Integer usu, Integer of);

    void deleteByOrdemFornecimentoId(Integer id);
}
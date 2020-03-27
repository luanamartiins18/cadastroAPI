package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.repositories.OrdemFornecimentoRepository;
import com.qintess.GerDemanda.repositories.UsuarioOrdemFornecimentoRepository;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoDTO;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoDetalhadoDTO;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoResumidaDTO;
import com.qintess.GerDemanda.service.mapper.OrdemFornecimentoMapper;
import com.qintess.GerDemanda.service.mapper.OrdemFornecimentoResumidaMapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdemFornecimentoService {

    public static final int STATUS_ATIVO = 1;
    public static final int SITUCAO_EM_EXECUCAO = 6;
    @Autowired
    OrdemFornecimentoRepository ordemFornecimentoRepository;
    @Autowired
    UsuarioOrdemFornecimentoRepository usuarioOrdemFornecimentoRepository;
    @Autowired
    OrdemFornecimentoResumidaMapper ordemFornecimentoResumidaMapper;
    @Autowired
    OrdemFornecimentoMapper ordemFornecimentoMapper;

    public List<Integer> getUsuariosOf(int idOf) {
        return (List<Integer>) this.usuarioOrdemFornecimentoRepository
                .findByStatusAndOrdemFornecimentoId(STATUS_ATIVO, idOf)
                .stream().map(obj -> obj.getUsuario().getId()).collect(Collectors.toList());
    }

    public OrdemFornecimentoResumidaDTO getSituacaoOf(int idOf) {
        return ordemFornecimentoResumidaMapper.toDto(this.ordemFornecimentoRepository.findById(idOf)
                .orElseThrow(() -> new ObjectNotFoundException("id", OrdemFornecimento.class.getName())));
    }

    public List<OrdemFornecimentoDetalhadoDTO> getOrdemDeFornecimento() {
        return this.ordemFornecimentoRepository.getOrdemDeFornecimento()
                .stream().map(obj -> new OrdemFornecimentoDetalhadoDTO(obj)).collect(Collectors.toList());
    }

    public List<OrdemFornecimentoDTO> getOrdemDeFornecimento(int id) {
        return ordemFornecimentoMapper.toDto(ordemFornecimentoRepository.findByIdAndSituacaoGentiId(id, SITUCAO_EM_EXECUCAO));
    }

    public void registraUsuSit(List<Integer> listaUsu, int situacao, int ofId, String referencia) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        String sql = "update ordem_forn set referencia = '" + referencia
                + "' where id = :ofId";
        Query query = em.createNativeQuery(sql);
        query.setParameter("ofId", ofId);
        query.executeUpdate();
        sql = "update ordem_forn set fk_situacao_usu = :sit where id = :ofId";
        query = em.createNativeQuery(sql);
        query.setParameter("sit", situacao);
        query.setParameter("ofId", ofId);
        query.executeUpdate();
        /*Atualização da situação da OF*/
        sql = "select fk_usuario from usuario_x_of where status = 1 and fk_ordem_forn = " + Integer.toString(ofId);
        query = em.createNativeQuery(sql);
        List<Integer> usuAtual = query.getResultList();
        String strListaUsu = "";
        for (int usu : listaUsu) {
            strListaUsu += (strListaUsu.equals("")) ? Integer.toString(usu) : "," + Integer.toString(usu);

            /*Se um usuário marcado no checkbox não estiver relacionado a OF, relaciona*/
            if (!usuAtual.contains(usu)) {
                sql = "INSERT INTO usuario_x_of (dt_criacao, fk_ordem_forn, fk_usuario, dt_exclusao, status) VALUES("
                        + "current_timestamp(), " + Integer.toString(ofId) + " , " + Integer.toString(usu) + " , null, 1);";

                query = em.createNativeQuery(sql);
                query.executeUpdate();
            }
        }
        sql = "update usuario_x_of set status = 0, dt_exclusao = current_timestamp() "
                + " where fk_ordem_forn = " + Integer.toString(ofId) + " and status = 1 and "
                + " fk_usuario not in (" + strListaUsu + ");";
        query = em.createNativeQuery(sql);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        entityManagerFactory.close();
    }


    public List<UsuarioOrdemFornecimento> getOrdensFornUsuario(int id) {
        return usuarioOrdemFornecimentoRepository.findByOrdemFornecimentoSituacaoUsuIdAndUsuarioIdAndDtExclusaoIsNullAndStatusOrderByOrdemFornecimentoSiglaDescricaoAsc(SITUCAO_EM_EXECUCAO,id,STATUS_ATIVO);
    }

}
package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.PersistenceHelper;
import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.repositories.OrdemFornecimentoRepository;
import com.qintess.GerDemanda.repositories.UsuarioOrdemFornecimentoRepository;
import com.qintess.GerDemanda.service.dto.*;
import com.qintess.GerDemanda.service.mapper.OrdemFornecimentoFiltradoMapper;
import com.qintess.GerDemanda.service.mapper.OrdemFornecimentoMapper;
import com.qintess.GerDemanda.service.mapper.OrdemFornecimentoResumidaMapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    @Autowired
    OrdemFornecimentoFiltradoMapper ordemFornecimentoFiltradoMapper;

    public List<Integer> getUsuariosOf(Integer idOf) {
        return (List<Integer>) this.usuarioOrdemFornecimentoRepository
                .findByStatusAndOrdemFornecimentoId(STATUS_ATIVO, idOf)
                .stream().map(obj -> obj.getUsuario().getId()).collect(Collectors.toList());
    }

    public OrdemFornecimento findOrdemFornecimentoById(Integer id) {
        return this.ordemFornecimentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", OrdemFornecimento.class.getName()));
    }

    public OrdemFornecimentoResumidaDTO findOrdemFornecimentoByIdDTO(Integer id) {
        return ordemFornecimentoResumidaMapper.toDto(this.findOrdemFornecimentoById(id));
    }

    public List<OrdensConcluidasDTO> findOrdensConcluidas() {
        return this.ordemFornecimentoRepository.findOrdensConcluidas()
                .stream().map(obj -> new OrdensConcluidasDTO(obj)).collect(Collectors.toList());
    }

    public List<OrdemFornecimentoDetalhadoDTO> getOrdemDeFornecimento() {
        return this.ordemFornecimentoRepository.getOrdemDeFornecimento()
                .stream().map(obj -> new OrdemFornecimentoDetalhadoDTO(obj)).collect(Collectors.toList());
    }

    public OrdemFornecimentoDTO getOrdemDeFornecimento(Integer id) {
        return ordemFornecimentoMapper.toDto(ordemFornecimentoRepository.findFirstByIdAndSituacaoGentiId(id, SITUCAO_EM_EXECUCAO));
    }

    public void registraUsuSit(List<Integer> listaUsu, int situacao, int ofId, String referencia) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
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

    }

    public List<OrdemFornecimentoFiltradoDTO> getOrdensFornUsuario(Integer id) {
        return ordemFornecimentoFiltradoMapper.toDto(usuarioOrdemFornecimentoRepository
                .findByOrdemFornecimentoSituacaoUsuIdAndUsuarioIdAndDtExclusaoIsNullAndStatusOrderByOrdemFornecimentoSiglaDescricaoAsc(SITUCAO_EM_EXECUCAO, id, STATUS_ATIVO));
    }

    public String getNumOf(Integer id) {
        return ordemFornecimentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", OrdemFornecimento.class.getName())).getNumeroOFGenti();
    }

    public UsuarioOrdemFornecimento getIdUsuOf(Integer usu, Integer of) {
        return usuarioOrdemFornecimentoRepository.
                findFirstByStatusAndUsuarioIdAndOrdemFornecimentoId(STATUS_ATIVO, usu, of);
    }
}
package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.model.UsuarioMensagem;
import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.repositories.OrdemFornecimentoRepository;
import com.qintess.GerDemanda.repositories.UsuarioOrdemFornecimentoRepository;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoDTO;
import com.qintess.GerDemanda.service.dto.OrdemFornecimentoResumidaDTO;
import com.qintess.GerDemanda.service.dto.UsuarioMensagemDTO;
import com.qintess.GerDemanda.service.dto.UsuarioOrdemFornecimentoDTO;
import com.qintess.GerDemanda.service.mapper.OrdemFornecimentoMapper;
import com.qintess.GerDemanda.service.mapper.UsuarioOrdemFornecimentoMapper;
import org.hibernate.ObjectNotFoundException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdemFornecimentoService {

    public static final int USUARIO_STATUS_ATIVO = 1;
    @Autowired
    OrdemFornecimentoRepository ordemFornecimentoRepository;
    @Autowired
    UsuarioOrdemFornecimentoRepository usuarioOrdemFornecimentoRepository;
    @Autowired
    OrdemFornecimentoMapper ordemFornecimentoMapper;

    public List<Integer> getUsuariosOf(int idOf) {
        return (List<Integer>) this.usuarioOrdemFornecimentoRepository
                .findByStatusAndOrdemFornecimentoId(USUARIO_STATUS_ATIVO,idOf)
                .stream().map(obj->obj.getUsuario().getId()).collect(Collectors.toList());
    }

    public OrdemFornecimentoResumidaDTO getSituacaoOf(int idOf) {
        return ordemFornecimentoMapper.toDto(this.ordemFornecimentoRepository.findById(idOf)
                .orElseThrow(() -> new ObjectNotFoundException("id", OrdemFornecimento.class.getName())));
    }

    public List<OrdemFornecimentoDTO> getOrdemDeFornecimento() {
        return this.ordemFornecimentoRepository.getOrdemDeFornecimento()
                .stream().map(obj-> new OrdemFornecimentoDTO(obj)).collect(Collectors.toList());
    }

    public OrdemFornecimento getOrdemDeFornecimento(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select distinct orf from OrdemFornecimento orf "
                + " left join fetch orf.listaUsuarios "
                + " inner join fetch orf.sigla "
                + " inner join fetch orf.situacaoGenti "
                + " left join fetch orf.situacaoUsu "
                + " where orf.situacaoGenti.id = 6 "
                + " and orf.id = :id ";
        TypedQuery<OrdemFornecimento> query = em.createQuery(sql, OrdemFornecimento.class);
        query.setParameter("id", id);
        List<OrdemFornecimento> ordemF = query.getResultList();
        em.close();
        entityManagerFactory.close();
        return ordemF.get(0);
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

    public List<HashMap<String, Object>> getOrdensFornUsuario(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select orf.id as idOf, orf.numero_OF_genti, orf.gerente_t, orf.responsavel_t, "
                + "orf.dt_abertura, orf.dt_previsao,  orf.usti_bb as ustibb_genti, "
                + "uof.dt_criacao as dt_encaminhamento, s1.descricao as situacao_genti, "
                + "s2.descricao as situacao_alm, "
                + "sg.descricao as sigla, orf.tema " +

                "from ordem_forn orf " +
                "inner join usuario_x_of uof " +
                "on orf.id = uof.fk_ordem_forn " +

                "inner join situacao s1 " +
                "on s1.id = orf.fk_situacao_genti " +

                "left join situacao s2 " +
                "on s2.id = orf.fk_situacao_usu " +

                "inner join sigla sg " +
                "on sg.id = orf.fk_sigla " +

                "where orf.fk_situacao_usu = 6 and  uof.fk_usuario = :id and uof.dt_exclusao is null and uof.status = 1 " +
                "order by sg.descricao";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        List<Object> lista = query.getResultList();
        List<HashMap<String, Object>> response = new ArrayList<HashMap<String, Object>>();
        for (Object obj : lista) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray objAtual = new JSONArray(obj);

            atual.put("idOf", objAtual.get(0));
            atual.put("numOF", objAtual.get(1));
            atual.put("gerenteT", objAtual.get(2));
            atual.put("responsavelT", objAtual.get(3));
            atual.put("dtAbertura", objAtual.get(4));
            atual.put("dtPrevisao", objAtual.get(5));
            atual.put("ustibbGenti", objAtual.get(6));
            atual.put("dtEncaminhamento", objAtual.get(7));
            atual.put("situacaoGenti", objAtual.get(8));
            atual.put("situacaoAlm", objAtual.get(9));
            atual.put("sigla", objAtual.get(10));
            atual.put("tema", objAtual.get(11));
            response.add(atual);
        }
        em.close();
        entityManagerFactory.close();
        return response;
    }
}

package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.model.UsuarioMensagem;
import com.qintess.GerDemanda.repositories.MensagemRepository;
import com.qintess.GerDemanda.repositories.UsuarioMensagemRepository;
import com.qintess.GerDemanda.service.dto.UsuarioMensagemDTO;
import com.qintess.GerDemanda.service.dto.MensagemDTO;
import com.qintess.GerDemanda.service.mapper.UsuarioMensagemDTOMapper;
import com.qintess.GerDemanda.service.mapper.MensagemMapper;
import org.hibernate.ObjectNotFoundException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensagemService {

    @Autowired
    MensagemRepository mensagemRepository;
    @Autowired
    UsuarioMensagemRepository usuarioMensagemRepository;
    @Autowired
    UsuarioService usuarioService;

    public List<UsuarioMensagemDTO> getAllMensagensByUsuarios(int idUsuario) {
        return this.usuarioMensagemRepository.findByMensagemStatusAndUsuarioMensId(1, idUsuario)
                .stream().map(obj -> UsuarioMensagemDTOMapper.modelToDto(obj)).collect(Collectors.toList());
    }

    public UsuarioMensagem getMensagemId(Integer id) {
        return usuarioMensagemRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", UsuarioMensagem.class.getName()));
    }

    public void marcaLida(Integer idMsgUsu) {
        UsuarioMensagem usuarioMensagem = this.getMensagemId(idMsgUsu);
        usuarioMensagem.setDtLeitura(new Date());
        this.usuarioMensagemRepository.save(usuarioMensagem);
    }

    @Transactional
    public void insereMensagem(MensagemDTO dto, String tipo) {
        List<Usuario> listUsu = usuarioService.getUsuarioBySigla(dto.getIdSigla());
        Mensagem obj = MensagemMapper.dtoToModel(dto);
        obj.setTipoMensagem(tipo);
        obj.setListaUsuarios(listUsu.stream().map(usuario -> new UsuarioMensagem(usuario, obj)).collect(Collectors.toList()));
        mensagemRepository.saveAndFlush(obj);
    }

    public List<HashMap<String, Object>> getMensagens() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select m.id, m.corpo, m.dt_criacao, m.dt_expiracao, m.tp_mensagem, m.status, u.nome , u2.nome as responsavel, m.titulo from mensagem m " +
                "inner join usuario_x_mensagem um on m.id = um.fk_mensagem " +
                "inner join usuario u on um.fk_usuario = u.id " +
                "inner join usuario u2 on m.fk_responsavel = u2.id "
                + "order by m.dt_expiracao asc;";
        Query query = em.createNativeQuery(sql);
        List<Object> lista = query.getResultList();
        List<HashMap<String, Object>> response = new ArrayList<HashMap<String, Object>>();
        for (Object obj : lista) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray objAtual = new JSONArray(obj);
            atual.put("idMsg", objAtual.get(0));
            atual.put("corpo", objAtual.get(1));
            atual.put("dtCriacao", objAtual.get(2));
            atual.put("dtExpiracao", objAtual.get(3));
            atual.put("tpMsg", objAtual.get(4));
            atual.put("status", objAtual.get(5));
            atual.put("usuario", objAtual.get(6));
            atual.put("responsavel", objAtual.get(7));
            atual.put("titulo", objAtual.get(8));
            response.add(atual);
        }
        em.close();
        entityManagerFactory.close();
        return response;
    }



    public List<HashMap<String, Object>> getMensagensColaborador(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select m.*, um.dt_leitura, um.id as idUM, s.descricao, u.nome from mensagem m " +
                "inner join usuario_x_mensagem um " +
                "on m.id = um.fk_mensagem " +
                "left join sigla s " +
                "on s.id = m.fk_sigla " +
                "inner join usuario u " +
                "on u.id = m.fk_responsavel " +
                "where m.status = 1 and  dt_leitura is null and curdate() <= m.dt_expiracao " +
                "and um.fk_usuario = :id ;";
        Query query = em.createNativeQuery(sql).setParameter("id", id);
        List<Object> lista = query.getResultList();
        List<HashMap<String, Object>> response = new ArrayList<HashMap<String, Object>>();
        for (Object obj : lista) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray objAtual = new JSONArray(obj);
            atual.put("idMsg", objAtual.get(0));
            atual.put("corpo", objAtual.get(1));
            atual.put("dtCriacao", objAtual.get(2));
            atual.put("dtExpiracao", objAtual.get(3));
            atual.put("tpMsg", objAtual.get(4));
            atual.put("status", objAtual.get(5));
            atual.put("responsavel", objAtual.get(12));
            atual.put("titulo", objAtual.get(7));
            atual.put("idUsuMsg", objAtual.get(10));
            atual.put("sigla", objAtual.get(11));
            response.add(atual);
        }
        em.close();
        entityManagerFactory.close();
        return response;
    }

    public List<HashMap<String, Object>> listaMensagens() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select m.*, u.nome, s.descricao from mensagem m "
                + "inner join usuario u on m.fk_responsavel = u.id "
                + " left join sigla s on s.id = m.fk_sigla "
                + "order by m.status desc, m.dt_expiracao asc;";
        Query query = em.createNativeQuery(sql);
        List<Object> lista = query.getResultList();
        List<HashMap<String, Object>> response = new ArrayList<HashMap<String, Object>>();
        for (Object obj : lista) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray objAtual = new JSONArray(obj);
            atual.put("idMsg", objAtual.get(0));
            atual.put("corpo", objAtual.get(1));
            atual.put("dtCriacao", objAtual.get(2));
            atual.put("dtExpiracao", objAtual.get(3));
            atual.put("tpMsg", objAtual.get(4));
            atual.put("status", objAtual.get(5));
            atual.put("responsavel", objAtual.get(9));
            atual.put("titulo", objAtual.get(7));
            atual.put("sigla", objAtual.get(10));
            response.add(atual);
        }
        em.close();
        entityManagerFactory.close();
        return response;
    }

    public List<HashMap<String, Object>> detalhaMensagem(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "select m.id as idM, m.corpo, m.dt_criacao, m.dt_expiracao, m.tp_mensagem, m.status, m.titulo," +
                "um.id as idUM, um.dt_leitura, u.id as idU, u.nome " +
                "from mensagem m " +
                "inner join usuario_X_mensagem um on m.id = um.fk_mensagem " +
                "inner join usuario u on um.fk_usuario = u.id " +
                "where m.id = " + Integer.toString(id);
        Query query = em.createNativeQuery(sql);
        List<Object> lista = query.getResultList();
        List<HashMap<String, Object>> response = new ArrayList<HashMap<String, Object>>();
        for (Object obj : lista) {
            HashMap<String, Object> atual = new HashMap<String, Object>();
            JSONArray objAtual = new JSONArray(obj);
            atual.put("idM", objAtual.get(0));
            atual.put("corpo", objAtual.get(1));
            atual.put("dtCriacao", objAtual.get(2));
            atual.put("dtExpiracao", objAtual.get(3));
            atual.put("tpMsg", objAtual.get(4));
            atual.put("status", objAtual.get(5));
            atual.put("titulo", objAtual.get(6));
            atual.put("idUM", objAtual.get(7));
            atual.put("dtLeitura", objAtual.get(8));
            atual.put("idU", objAtual.get(9));
            atual.put("nomeUsu", objAtual.get(10));
            response.add(atual);
        }
        em.close();
        entityManagerFactory.close();
        return response;
    }

    public void alteraStatusMsg(int idMsg, String acao) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "";
        if (acao.equals("desativar")) {
            sql = "UPDATE mensagem set status = 0 where id = :id";
        } else {
            sql = "UPDATE mensagem set status = 1 where id = :id";
        }
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", idMsg);
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        entityManagerFactory.close();
    }
}


































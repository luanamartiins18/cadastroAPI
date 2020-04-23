package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.PersistenceHelper;
import com.qintess.GerDemanda.model.VersaoGuia;
import com.qintess.GerDemanda.repositories.VersaoGuiaRepository;
import com.qintess.GerDemanda.service.dto.VersaoGuiaDTO;
import com.qintess.GerDemanda.service.mapper.VersaoGuiaMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Service
public class VersaoGuiaService {

    @Autowired
    VersaoGuiaRepository versaoGuiaRepository;

    @Autowired
    VersaoGuiaMapper versaoGuiaMapper;


    public VersaoGuiaDTO getVersaoAtualGuia() {
        List<VersaoGuia> versaoGuia = versaoGuiaRepository.findAll();
        if (versaoGuia.isEmpty()) {
            return null;
        }
        return versaoGuiaMapper.toDto(versaoGuia.get(0));
    }

    public boolean atualizaVersaoGuia(String paramRequest) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        JSONObject json = new JSONObject(paramRequest);
        String versao = json.getString("versao");
        try {
            em.getTransaction().begin();
            String sql = "DELETE FROM versao_guia where id > 0";
            Query query = em.createNativeQuery(sql);
            query.executeUpdate();
            sql = "INSERT INTO versao_guia(descricao) values(:descricao) ; ";
            query = em.createNativeQuery(sql);
            query.setParameter("descricao", versao);
            query.executeUpdate();
        } catch (Exception excp) {
            em.getTransaction().rollback();
            em.close();
            throw excp;
        }
        em.getTransaction().commit();
        em.close();
        return true;
    }
}
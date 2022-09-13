package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.HistoricoUsuario;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.repositories.HistoricoRepository;
import com.qintess.GerDemanda.service.dto.HistoricoUsuarioDTO;
import com.qintess.GerDemanda.service.mapper.HistoricoUsuarioMapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class HistoricoUsuarioService {

    @Autowired
    private HistoricoUsuarioDTO historicoUsuario;

    @Autowired
    private HistoricoUsuario historico;
    @Autowired
    private HistoricoUsuarioMapper historicoMapper;

    @Autowired
    private HistoricoRepository historicoRepository;

    @Transactional
    public void insereHistoricoUsuario(HistoricoUsuario obj) {
        //HistoricoUsuario obj = historicoMapper.toEntity(dto);
        HistoricoUsuario usuario = new HistoricoUsuario();
        usuario.setData_final(obj.getData_final());
        usuario.setData_inicio(obj.getData_inicio());
        usuario.setCargo(obj.getCargo());
        usuario.setUsuario(obj.getUsuario());
        historicoRepository.save(usuario);
    }

    public HistoricoUsuario findByUsuario(Integer id) {
        return historicoRepository.findByUsuario(id);

    }

    public void alteraDataFinal (Integer id, Date dtFinal ) {
        HistoricoUsuario historicoUsuario = this.findByUsuario(id);
        historicoUsuario.setData_final(dtFinal);
        historicoRepository.save(historicoUsuario);

    }
}

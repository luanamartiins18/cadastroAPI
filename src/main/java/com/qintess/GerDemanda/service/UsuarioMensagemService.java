package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.UsuarioMensagem;
import com.qintess.GerDemanda.repositories.UsuarioMensagemRepository;
import com.qintess.GerDemanda.service.dto.UsuarioMensagemDTO;
import com.qintess.GerDemanda.service.mapper.UsuarioMensagemMapper;
import com.qintess.GerDemanda.service.util.DateUtil;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioMensagemService {

    public static final int MENSAGEM_STATUS_ATIVO = 1;
    @Autowired
    UsuarioMensagemRepository usuarioMensagemRepository;
    @Autowired
    UsuarioMensagemMapper usuarioMensagemMapper;

    public List<UsuarioMensagemDTO> getAllMensagensByUsuarios(Integer idUsuario) {
        return usuarioMensagemMapper.toDto(this.usuarioMensagemRepository.findByMensagemStatusAndUsuarioMensId(1, idUsuario));
    }

    public UsuarioMensagem findMensagemUsuarioById(Integer id) {
        return usuarioMensagemRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", UsuarioMensagem.class.getName()));
    }

    public void marcaLida(Integer idMsgUsu) {
        UsuarioMensagem usuarioMensagem = this.findMensagemUsuarioById(idMsgUsu);
        usuarioMensagem.setDtLeitura(DateUtil.getCurrentDateTime());
        this.usuarioMensagemRepository.save(usuarioMensagem);
    }

    public List<UsuarioMensagemDTO> getMensagensColaborador(Integer usuarioMensId) {
        return usuarioMensagemMapper.toDto(this.usuarioMensagemRepository
                .findByDtLeituraIsNullAndMensagemDtExpiracaoGreaterThanEqualAndMensagemStatusAndUsuarioMensId(
                        DateUtil.getCurrentDateTimeZero(), MENSAGEM_STATUS_ATIVO, usuarioMensId));
    }

    public List<UsuarioMensagemDTO> detalhaMensagem(Integer idMensagem) {
        return usuarioMensagemMapper.toDto(this.usuarioMensagemRepository.findByMensagemId(idMensagem));
    }
}
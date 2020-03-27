package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.model.UsuarioMensagem;
import com.qintess.GerDemanda.repositories.MensagemRepository;
import com.qintess.GerDemanda.repositories.UsuarioMensagemRepository;
import com.qintess.GerDemanda.service.dto.MensagemDTO;
import com.qintess.GerDemanda.service.dto.MensagemInDTO;
import com.qintess.GerDemanda.service.mapper.MensagemInMapper;
import com.qintess.GerDemanda.service.mapper.MensagemMapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Autowired
    MensagemMapper mensagemMapper;

    @Transactional
    public void insereMensagem(MensagemInDTO dto, String tipo) {
        List<Usuario> listUsu = usuarioService.getUsuarioBySigla(dto.getIdSigla());
        Mensagem obj = MensagemInMapper.dtoToModel(dto);
        obj.setTipoMensagem(tipo);
        obj.setListaUsuarios(listUsu.stream().map(usuario -> new UsuarioMensagem(usuario, obj)).collect(Collectors.toList()));
        mensagemRepository.saveAndFlush(obj);
    }

    public List<MensagemDTO> getMensagem() {
        return mensagemMapper.toDto(mensagemRepository.findByOrderByDtExpiracaoAsc());
    }

    public Mensagem findById(Integer id) {
        return mensagemRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", Mensagem.class.getName()));
    }

    public void alteraStatusMsg(int idMsg, String acao) {
        Mensagem mensagem = this.findById(idMsg);
        mensagem.setStatus(1);
        if (acao.equals("desativar")) {
            mensagem.setStatus(0);
        }
        this.mensagemRepository.save(mensagem);
    }

}


































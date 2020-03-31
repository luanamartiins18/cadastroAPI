package com.qintess.GerDemanda.service;


import com.qintess.GerDemanda.model.Mensagem;
import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.model.UsuarioMensagem;
import com.qintess.GerDemanda.repositories.MensagemRepository;
import com.qintess.GerDemanda.repositories.UsuarioMensagemRepository;
import com.qintess.GerDemanda.service.dto.MensagemDTO;
import com.qintess.GerDemanda.service.dto.MensagemInDTO;
import com.qintess.GerDemanda.service.mapper.MensagemBuilder;
import com.qintess.GerDemanda.service.mapper.MensagemMapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensagemService {

    public static final String GERAL = "GERAL";
    public static final String SIGLA = "SIGLA";
    @Autowired
    MensagemRepository mensagemRepository;
    @Autowired
    UsuarioMensagemRepository usuarioMensagemRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    MensagemMapper mensagemMapper;

    public void insereMensagemGeral(MensagemInDTO dto) {
        insert(dto, GERAL, usuarioService.getUsuariosAtivos());
    }

    public void insereMensagemSigla(MensagemInDTO dto) {
        insert(dto, SIGLA, usuarioService.getUsuariosBySigla(dto.getIdSigla()));
    }

    @Transactional
    private void insert(MensagemInDTO dto, String tipo, List<Usuario> listUsu) {
        Mensagem obj = MensagemBuilder.toDtoBuild(dto);
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


































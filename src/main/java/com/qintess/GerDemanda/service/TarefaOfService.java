package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.ItemGuia;
import com.qintess.GerDemanda.model.Situacao;
import com.qintess.GerDemanda.model.TarefaOf;
import com.qintess.GerDemanda.model.UsuarioOrdemFornecimento;
import com.qintess.GerDemanda.repositories.TarefaOfRepository;
import com.qintess.GerDemanda.service.dto.TarefaOfDTO;
import com.qintess.GerDemanda.service.dto.TarefaOfDetalhadoDTO;
import com.qintess.GerDemanda.service.dto.TarefaOfValorDTO;
import com.qintess.GerDemanda.service.mapper.TarefaOfDetalhadoMapper;
import com.qintess.GerDemanda.service.mapper.TarefaOfMapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service

public class TarefaOfService {

    @Autowired
    TarefaOfRepository tarefaOfRepository;

    @Autowired
    OrdemFornecimentoService ordemFornecimentoService;

    @Autowired
    TarefaOfMapper tarefaOfMapper;

    @Autowired
    TarefaOfDetalhadoMapper tarefaOfDetalhadoMapper;

    public TarefaOf findById(Integer id) {
        return tarefaOfRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", TarefaOf.class.getName()));
    }

    public void deletaTarefa(Integer id) {
        TarefaOf tarefaOf = this.findById(id);
        tarefaOfRepository.delete(tarefaOf);
    }

    public void alteraSituacaoTarefa(Integer idTrf, Integer idSit) {
        TarefaOf tarefaOf = this.findById(idTrf);
        tarefaOf.setSituacao(Situacao.builder().id(idSit).build());
        tarefaOfRepository.save(tarefaOf);
    }

    @Transactional
    public boolean insereTarefa(TarefaOfDTO dto) {
        TarefaOf obj = tarefaOfMapper.toEntity(dto);
        obj.setSituacao(Situacao.builder().id(6).build());
        obj.setPerfil((obj.getPerfil().equals('1')) ? "Baixa" : "Alta");
        Integer idUsu = obj.getUsuarioOrdemFornecimento().getUsuario().getId();
        Integer idOf = obj.getUsuarioOrdemFornecimento().getOrdemFornecimento().getId();
        UsuarioOrdemFornecimento usuOf = ordemFornecimentoService.getIdUsuOf(idUsu, idOf);
        obj.setUsuarioOrdemFornecimento(usuOf);
        tarefaOfRepository.save(obj);
        return true;
    }

    private void tarefaOfMapperUpdate(TarefaOfDTO dto, TarefaOf objOld) {
        TarefaOf objNew = tarefaOfMapper.toEntity(dto);
        objOld.setHistoria(objNew.getHistoria());
        objOld.setSprint(objNew.getSprint());
        objOld.setObservacao(objNew.getObservacao());
        objOld.setArtefato(objNew.getArtefato());
        objOld.setQuantidade(objNew.getQuantidade());
        objOld.setNum_tarefa(objNew.getNum_tarefa());
        objNew.setItemGuia(ItemGuia.builder().id(objNew.getItemGuia().getId()).build());
        objOld.setPerfil((objNew.getPerfil().equals('1')) ? "Baixa" : "Alta");
        objOld.setId(objNew.getId());
    }

    @Transactional
    public void atualizaTarefa(Integer idTrfOf, TarefaOfDTO dto) {
        TarefaOf objOld = findById(idTrfOf);
        tarefaOfMapperUpdate(dto, objOld);
        tarefaOfRepository.save(objOld);
    }

    public List<TarefaOfDetalhadoDTO> getTarefasUsuarioDTO(Integer idUsu, Integer idOf) {
        return tarefaOfDetalhadoMapper.toDto(getTarefaUsuarios(idUsu, idOf));
    }

    private List<TarefaOf> getTarefaUsuarios(Integer idUsu, Integer idOf) {
        return tarefaOfRepository
                .findByUsuarioOrdemFornecimentoUsuarioIdAndUsuarioOrdemFornecimentoOrdemFornecimentoId(idUsu, idOf);
    }

    private List<TarefaOf> getTarefaUsuario(Integer idOf) {
        return tarefaOfRepository
                .findByUsuarioOrdemFornecimentoOrdemFornecimentoId(idOf);
    }

    public TarefaOfValorDTO getValorTarefa(Integer idUsu, Integer idOf) {
        TarefaOfValorDTO tarefaOfValorDTO = new TarefaOfValorDTO();
        TarefaOfValorDTO tarefaOfValorDTOTotal = new TarefaOfValorDTO();

        this.getTarefaUsuarios(idUsu, idOf).stream().forEach(obj -> calTarefaOfValor(tarefaOfValorDTO, obj));
        this.getTarefaUsuario(idOf).stream().forEach(obj -> calTarefaOfValor(tarefaOfValorDTOTotal, obj));

        tarefaOfValorDTO.setValorExecutadoTotal(tarefaOfValorDTOTotal.getValorExecutado());
        tarefaOfValorDTO.setValorPlanejadoTotal(tarefaOfValorDTOTotal.getValorPlanejado());
        return tarefaOfValorDTO;
    }

    private void calTarefaOfValor(TarefaOfValorDTO tarefaOfValorDTO, TarefaOf obj) {
        if (obj.getSituacao().equals("4") || obj.getSituacao().equals("8")) {
            tarefaOfValorDTO.setValorExecutado(tarefaOfValorDTO.getValorExecutado() + obj.getItemGuia().getValor());
        }
        if (!obj.getSituacao().equals("2") || !obj.getSituacao().equals("5")) {
            tarefaOfValorDTO.setValorPlanejado(tarefaOfValorDTO.getValorPlanejado() + obj.getItemGuia().getValor());
        }
    }
}
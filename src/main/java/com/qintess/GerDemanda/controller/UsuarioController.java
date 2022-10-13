package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.repositories.*;
import com.qintess.GerDemanda.model.*;
import com.qintess.GerDemanda.service.*;
import com.qintess.GerDemanda.service.dto.*;
import com.qintess.GerDemanda.service.mapper.CargoMapper;
import com.qintess.GerDemanda.service.mapper.OperacaoMapper;
import  com.qintess.GerDemanda.service.mapper.UsuarioMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    HistoricoUsuarioService historicoUsuarioService;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    CargoService cargoService;

    @Autowired
    CargoMapper cargoMapper;

    @Autowired
    OperacaoMapper operacaoMapper;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Autowired
    TipoService tipoService;

    @Autowired
    Buservice buService;

    @Autowired
    HistoricoUsuario historicoUsuario;

    @Autowired
    HistoricoOperacao historicoOperacao;

    @Autowired
    HistoricoOperacaoService historicoOperacaoService;



    @GetMapping("/usuario/{re}")
    ResponseEntity<UsuarioDTO> getUsuario(@PathVariable String re) {
        UsuarioDTO usuario = usuarioService.getUsuarioByRe(re);
        return (usuario == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuario);
    }


    @GetMapping("/usuarios")
    ResponseEntity<List<UsuarioDTO>> getListaUsuarios() {
        List<UsuarioDTO> listausuario = usuarioService.getListaUsuarios();
        return (listausuario.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listausuario);
    }

    @GetMapping("/usuariosoperacao/{idOperacao}")
    ResponseEntity<List<UsuarioDTO>> getListaUsuariosPorOperacao(@PathVariable Integer idOperacao) {
        List<UsuarioDTO> listausuario = usuarioService.getListaUsuariosPorOperacao(idOperacao);
        return (listausuario.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listausuario);
    }

    @GetMapping("/historico")
    ResponseEntity<List<HistoricoUsuarioDTO>> getListaHistorico() {
        List<HistoricoUsuarioDTO> listahistorico = usuarioService.getListaHistorico();
        return (listahistorico.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistorico);
    }

    @GetMapping("/historico/{re}")
    ResponseEntity<List<HistoricoUsuarioDTO>> getListaHistoricoComRe(@PathVariable String re) {
        Usuario usuario = usuarioMapper.toEntity(usuarioService.getUsuarioByRe(re));
        List<HistoricoUsuarioDTO> listahistorico = historicoUsuarioService.findByUsuarioOrderByDataInicioDesc(usuario.getId());
        return (listahistorico.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistorico);
    }

    @GetMapping("/historicooperacao")
    ResponseEntity<List<HistoricoOperacaoDTO>> getListaHistoricoOperacao() {
        List<HistoricoOperacaoDTO> listahistorico = usuarioService.getListaHistoricoOperacao();
        return (listahistorico.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistorico);
    }


    @GetMapping("/historicooperacao/{re}")
    ResponseEntity<List<HistoricoOperacaoDTO>> getListaHistoricoOperacaoComRe(@PathVariable String re) {
        Usuario usuario = usuarioMapper.toEntity(usuarioService.getUsuarioByRe(re));
        List<HistoricoOperacaoDTO> listahistoricoOperacao = historicoOperacaoService.findByOperacaoOrderByDataInicioDesc(usuario.getId());
        return (listahistoricoOperacao.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistoricoOperacao);
    }


    @DeleteMapping("/historico/{id}")
    public ResponseEntity<?> deleteHistorico(@PathVariable Integer id) {
        historicoUsuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{re}/cargo")
    ResponseEntity<CargoDTO> getCargoUsuarioByRe(@PathVariable String re) {
        CargoDTO cargo = cargoService.getCargoUsuarioByRe(re);
        return (cargo == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(cargo);
    }

    @GetMapping("/usuario/{re}/tipo")
    ResponseEntity<TipoDTO> getTipoUsuarioByRe(@PathVariable String re) {
        TipoDTO tipo = tipoService.getTipoUsuarioByRe(re);
        return (tipo == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(tipo);
    }

    @GetMapping("/usuario/{re}/bu")
    ResponseEntity<BuDTO> getBuUsuarioByRe(@PathVariable String re) {
        BuDTO bu = buService.getBuUsuarioByRe(re);
        return (bu == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(bu);
    }

    @GetMapping("/usuarios/{id}")
    ResponseEntity<UsuarioDTO> getUsuarioId(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.findByIdDTO(id);
        return Objects.isNull(usuario) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuario);
    }


    @PostMapping(value = "/usuarios")
    public ResponseEntity<String> insereUsuario(@Valid @RequestBody UsuarioDTO dto) {
        usuarioService.insereUsuario(dto);
        historicoUsuario.setVigente("Sim");
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/funcao")
    public ResponseEntity<String> insereFuncao(@Valid @RequestBody FuncaoDTO dto) {
        Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        usuarioService.atualizaFuncao(usuario.getId(), dto);
        Cargo cargo = cargoMapper.toEntity(dto.getCargo());
        Date dt = new Date();
        //Atualizar historico anterior
        HistoricoUsuario historico = historicoUsuarioService.findUltimoHistoricoByUsuario(usuario.getId());
        if(historico != null) {
            historicoUsuarioService.updateUltimoHistorico(dt, "Não", historico.getId());
        }
        //Insere novo historico
        historicoUsuario.setData_inicio(dt);
        historicoUsuario.setCargo(cargo);
        historicoUsuario.setVigente("Sim");
        historicoUsuario.setUsuario(usuario);
        historicoUsuarioService.insereHistoricoUsuario(historicoUsuario);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/contrato")
    public ResponseEntity<String> insereContrato(@Valid @RequestBody ContratoDTO dto) {
        Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        usuarioService.atualizaContrato(usuario.getId(), dto);
        Operacao operacao = operacaoMapper.toEntity(dto.getOperacao());
        Date dt = new Date();
        //Atualizar historico anterior
        HistoricoOperacao historico = historicoOperacaoService.findUltimoHistoricoByOperacao(usuario.getId());
        if(historico != null) {
            historicoOperacaoService.updateUltimoHistoricoOperacao(dt, "Não", historico.getId());
        }
        //Insere novo historico
        historicoOperacao.setData_inicio(dt);
        historicoOperacao.setOperacao(operacao);
        historicoOperacao.setVigente("Sim");
        historicoOperacao.setUsuario(usuario);
        historicoOperacaoService.insereHistoricoOperacao(historicoOperacao);
        return ResponseEntity.ok().build();
    }


    @PutMapping(value = "/usuarios/{id}")
    public ResponseEntity<String> atualizaUsuario (@PathVariable Integer id, @Valid @RequestBody UsuarioDTO dto) {
        usuarioService.updateUsuario(id, dto);
        Date dt = new Date();
        Usuario usuario = usuarioService.findById(id);
        historicoUsuario.setData_inicio(dt);
        historicoUsuario.setVigente("Sim");
        historicoUsuario.setUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/usuarios/{id}")
    public ResponseEntity<String> deleteUsuario (@PathVariable Integer id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/usuario-status")
    public ResponseEntity<String> alteraStatus(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        Integer id = json.getInt("id");
        String acao = json.getString("acao");
        usuarioService.alteraStatus(id, acao);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
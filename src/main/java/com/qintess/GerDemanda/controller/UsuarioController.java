package com.qintess.GerDemanda.controller;


import com.qintess.GerDemanda.model.*;
import com.qintess.GerDemanda.service.*;
import com.qintess.GerDemanda.service.dto.*;
import com.qintess.GerDemanda.service.exception.InvalidTokenException;
import com.qintess.GerDemanda.service.exception.UsuarioNotFoundException;
import com.qintess.GerDemanda.service.mapper.*;
import com.qintess.GerDemanda.service.repositories.PasswordResetTokenRepository;
import com.qintess.GerDemanda.service.repositories.UsuarioRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.util.ArrayList;
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
    HistoricoPerfilService historicoPerfilService;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    CargoService cargoService;

    @Autowired
    PerfilService perfilService;

    @Autowired
    CargoMapper cargoMapper;

    @Autowired
    PerfilMapper perfilMapper;

    @Autowired
    ContratoMapper contratoMapper;

    @Autowired
    ModeloMapper modeloMapper;

    @Autowired
    EquipamentoMapper equipamentoMapper;

    @Autowired
    MemoriaMapper memoriaMapper;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TipoService tipoService;

    @Autowired
    ModeloService modeloService;

    @Autowired
    EquipamentoService equipamentoService;

    @Autowired
    MemoriaService memoriaService;

    @Autowired
    Buservice buService;

    @Autowired
    HistoricoUsuario historicoUsuario;

    @Autowired
    HistoricoPerfil historicoPerfil;

    @Autowired
    HistoricoOperacao historicoOperacao;

    @Autowired
    HistoricoMaquinas historicoMaquinas;

    @Autowired
    HistoricoMaquinasService historicoMaquinasService;

    @Autowired
    HistoricoOperacaoService historicoOperacaoService;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;



    @GetMapping("/usuario/{re}")
    ResponseEntity<UsuarioDTO> getUsuarioRe(@PathVariable String re) {
        UsuarioDTO usuario = usuarioService.getUsuarioByRe(re);
        return (usuario == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuario);
    }

    @GetMapping("/usuarios")
    ResponseEntity<List<UsuarioDTO>> getListaUsuarios() {
        List<UsuarioDTO> listausuario = usuarioService.getListaUsuarios();
        return (listausuario.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listausuario);
    }

    @GetMapping("/usuariosoperacao/{idContrato}")
    ResponseEntity<List<UsuarioDTO>> getListaUsuariosPorOperacao(@PathVariable Integer idContrato) {
        List<UsuarioDTO> listausuario = usuarioService.getListaUsuariosPorOperacao(idContrato);
        return (listausuario.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listausuario);
    }

    @GetMapping("/historico")
    ResponseEntity<List<HistoricoUsuarioDTO>> getListaHistorico() {
        List<HistoricoUsuarioDTO> listahistorico = usuarioService.getListaHistorico();
        return (listahistorico.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistorico);
    }

    @GetMapping("/historicoperfil")
    ResponseEntity<List<HistoricoPerfilDTO>> getListaHistoricoPerfil() {
        List<HistoricoPerfilDTO> listahistorico = usuarioService.getListaHistoricoPerfil();
        return (listahistorico.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistorico);
    }

    @GetMapping("/historicomaquinas")
    ResponseEntity<List<HistoricoMaquinasDTO>> getListaHistoricoMaquinas() {
        List<HistoricoMaquinasDTO> listahistorico = usuarioService.getListaHistoricoMaquinas();
        return (listahistorico.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistorico);
    }

    @GetMapping("/historico/{re}")
    ResponseEntity<List<HistoricoUsuarioDTO>> getListaHistoricoComRe(@PathVariable String re) {
        Usuario usuario = usuarioMapper.toEntity(usuarioService.getUsuarioByRe(re));
        List<HistoricoUsuarioDTO> listahistorico = historicoUsuarioService.findByUsuarioOrderByDataInicioDesc(usuario.getId());
        return (listahistorico.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistorico);
    }

    @GetMapping("/historicoperfil/{re}")
    ResponseEntity<List<HistoricoPerfilDTO>> getListaHistoricoPerfilComRe(@PathVariable String re) {
        Usuario usuario = usuarioMapper.toEntity(usuarioService.getUsuarioByRe(re));
        List<HistoricoPerfilDTO> listahistorico = historicoPerfilService.findByUsuarioOrderByDataInicioDesc(usuario.getId());
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


    @GetMapping("/historicomaquinas/{re}")
    ResponseEntity<List<HistoricoMaquinasDTO>> getListaHistoricoMaquinasComRe(@PathVariable String re) {
        UsuarioDTO usuarioDTO = usuarioService.getUsuarioByRe(re);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        List<HistoricoMaquinasDTO> listahistoricoMaquinas = historicoMaquinasService.findByMaquinasOrderByDataInicioDesc(usuario.getId());
        ArrayList<HistoricoMaquinasDTO> newHistoricoMaquinas = new ArrayList<HistoricoMaquinasDTO>();
        for (HistoricoMaquinasDTO listahistorico : listahistoricoMaquinas){
            listahistorico.setUsuario(usuarioDTO);
            newHistoricoMaquinas.add(listahistorico);
        }
        return (newHistoricoMaquinas.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(newHistoricoMaquinas);
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

    @GetMapping("/usuario/{re}/perfil")
    ResponseEntity<PerfilDTO> getPerfilUsuarioByRe(@PathVariable String re) {
        PerfilDTO perfil = perfilService.getPerfilUsuarioByRe(re);
        return (perfil == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(perfil);
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

    @GetMapping("/usuario/{re}/equipamento")
    ResponseEntity<EquipamentoDTO> getEquipamentoUsuarioByRe(@PathVariable String re) {
        EquipamentoDTO equipamento = equipamentoService.getEquipamentoUsuarioByRe(re);
        return (equipamento == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(equipamento);
    }

    @GetMapping("/usuario/{re}/modelo")
    ResponseEntity<ModeloDTO> getModeloUsuarioByRe(@PathVariable String re) {
        ModeloDTO modelo = modeloService.getModeloUsuarioByRe(re);
        return (modelo == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(modelo);
    }

    @GetMapping("/usuario/{re}/memoria")
    ResponseEntity<MemoriaDTO> getMemoriaUsuarioByRe(@PathVariable String re) {
        MemoriaDTO memoria = memoriaService.getMemoriaUsuarioByRe(re);
        return (memoria == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(memoria);
    }

    @GetMapping("/usuarios/{id}")
    ResponseEntity<UsuarioDTO> getUsuarioId(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.findByIdDTO(id);
        return Objects.isNull(usuario) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(usuario);
    }


    @GetMapping("/historicomaquinaslista/{id}")
    ResponseEntity<HistoricoMaquinasDTO> getHistoricoMaquinasId(@PathVariable Integer id) {
        HistoricoMaquinasDTO maquinas = usuarioService.findByIdHistoricoDTO(id);
        return Objects.isNull(maquinas) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(maquinas);
    }


    @PostMapping(value = "/usuarios")
    public ResponseEntity<String> insereUsuario(@Valid @RequestBody UsuarioDTO dto) {
        usuarioService.insereUsuario(dto);
        historicoUsuario.setVigente("Sim");
        return ResponseEntity.ok().build();
    }

    //@PostMapping(value = "/funcao")
    //public ResponseEntity<String> insereFuncao(@Valid @RequestBody FuncaoDTO dto) {
      //  Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        //usuarioService.atualizaFuncao(usuario.getId(), dto);
    //  Cargo cargo = cargoMapper.toEntity(dto.getCargo());
    //  Date dt = new Date();
        //Atualizar historico anterior
    // HistoricoUsuario historico = historicoUsuarioService.findUltimoHistoricoByUsuario(usuario.getId());
    //  if(historico != null) {
        //      historicoUsuarioService.updateUltimoHistorico(dt, "Não", historico.getId());
    //  }
        //Insere novo historico
    //  historicoUsuario.setData_inicio(dt);
    //  historicoUsuario.setCargo(cargo);
    //  historicoUsuario.setVigente("Sim");
    //   historicoUsuario.setUsuario(usuario);
    //  historicoUsuarioService.insereHistoricoUsuario(historicoUsuario);
    //  return ResponseEntity.ok().build();
    // }

    @PostMapping(value = "/perfil")
    public ResponseEntity<String> inserePerfil(@Valid @RequestBody PerfilHDTO dto) {
        Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        usuarioService.atualizaPerfil(usuario.getId(), dto);
        Perfil perfil = perfilMapper.toEntity(dto.getPerfil());
        Date dt = new Date();
        //Atualizar historico anterior
        HistoricoPerfil historico = historicoPerfilService.findUltimoHistoricoByUsuario(usuario.getId());
        if(historico != null) {
            historicoPerfilService.updateUltimoHistorico(dt, "Não", historico.getId());
        }
        //Insere novo historico
        historicoPerfil.setData_inicio(dt);
        historicoPerfil.setPerfil(perfil);
        historicoPerfil.setVigente("Sim");
        historicoPerfil.setUsuario(usuario);
        historicoPerfilService.insereHistoricoPerfil(historicoPerfil);
        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/contrato")
    public ResponseEntity<String> insereContrato(@Valid @RequestBody ContratoHDTO dto) {
        Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        usuarioService.atualizaContrato(usuario.getId(), dto);
        Date dt = new Date();
        //Atualizar historico anterior
        HistoricoOperacao historico = historicoOperacaoService.findUltimoHistoricoByOperacao(usuario.getId());
        if(historico != null) {
            historicoOperacaoService.updateUltimoHistoricoOperacao(dt, "Não", historico.getId());
        }
        //Insere novo historico
        historicoOperacao.setData_inicio(dt);
        historicoOperacao.setVigente("Sim");
        historicoOperacao.setUsuario(usuario);
        historicoOperacaoService.insereHistoricoOperacao(historicoOperacao);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/maquinas")
    public ResponseEntity<String> insereMaquinas(@Valid @RequestBody MaquinasDTO dto) {
        Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        usuarioService.atualizaMaquinas(usuario.getId(), dto);
        Modelo modelo = modeloMapper.toEntity(dto.getModelo());
        Equipamento equipamento = equipamentoMapper.toEntity(dto.getEquipamento());
        Memoria memoria = memoriaMapper.toEntity(dto.getMemoria());
        //Atualizar historico anterior
        historicoMaquinas.setData_inicio(dto.getData_inicio());
        historicoMaquinas.setModelo(modelo);
        historicoMaquinas.setMemoria(memoria);
        historicoMaquinas.setEquipamento(equipamento);
        historicoMaquinas.setVigente("Sim");
        historicoMaquinas.setPatrimonio(dto.getPatrimonio());
        historicoMaquinas.setTag(dto.getTag());
        historicoMaquinas.setUsuario(usuario);
        historicoMaquinasService.insereHistoricoMaquinas(historicoMaquinas);
        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/atualizarmaquinas")
    public ResponseEntity<String> atualizarMaquinas(@Valid @RequestBody MaquinasDTO dto) {
        Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        usuarioService.atualizaMaquinas(usuario.getId(), dto);
        Date dt = new Date();
        //Atualizar historico anterior
        HistoricoMaquinas historico = historicoMaquinasService.findUltimoHistoricoByMaquinas(usuario.getId());
        if(historico != null) {
           historicoMaquinasService.updateUltimoHistoricoMaquinas(dto.getData_inicio(), dto.getData_final(), "Não", historico.getId());
        }
        return ResponseEntity.ok().build();
    }



    @PutMapping(value = "/usuarios/{id}")
    public ResponseEntity<String> atualizaUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO dto) {
        usuarioService.updateUsuario(id, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/usuariosenha/{id}")
    public ResponseEntity<String> atualizaUsuarioSenha(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO dto) {
        usuarioService.updateSenhaUsuario(id, dto);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/usuario-status")
    public ResponseEntity<String> alteraStatus(@RequestBody String param) {
        JSONObject json = new JSONObject(param);
        Integer id = json.getInt("id");
        String acao = json.getString("acao");
        usuarioService.alteraStatus(id, acao);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/usuario/token/{token}")
    public Usuario getUsuarioByToken(@PathVariable String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            throw new InvalidTokenException();
        }
        Usuario usuario = passwordResetToken.getUsuario();
        if (usuario == null) {
            throw new UsuarioNotFoundException();
        }
        return usuario;
    }


}
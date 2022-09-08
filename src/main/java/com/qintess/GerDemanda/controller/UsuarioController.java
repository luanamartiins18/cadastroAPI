package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.repositories.*;
import com.qintess.GerDemanda.model.*;
import com.qintess.GerDemanda.service.*;
import com.qintess.GerDemanda.service.dto.*;
import com.qintess.GerDemanda.service.mapper.CargoMapper;
import com.qintess.GerDemanda.service.mapper.UsuarioMapper;
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
    HistoricoUsuarioDTO historicoUsuarioDTO;

    @Autowired
    CargoService cargoService;

    @Autowired
    CargoMapper cargoMapper;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Autowired
    TipoService tipoService;

    @Autowired
    Buservice buService;

    @Autowired
    HistoricoUsuario historicoUsuario;




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
        Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        //Cargo cargo = cargoMapper.toEntity(dto.getCargo());
        Date dt = new Date();
        historicoUsuario.setData_inicio(dt);
        //historicoUsuario.setCargo(cargo);
        historicoUsuario.setUsuario(usuario);
        //historicoUsuarioService.insereHistoricoUsuario(historicoUsuario);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/funcao")
    public ResponseEntity<String> insereFuncao(@Valid @RequestBody FuncaoDTO dto) {
        Usuario usuario = usuarioRepository.findFirstByCodigoRe(dto.getCodigoRe());
        usuarioService.atualizaFuncao(usuario.getId(), dto);
        Cargo cargo = cargoMapper.toEntity(dto.getCargo());
        Date dt = new Date();
        historicoUsuario.setData_inicio(dt);
        historicoUsuario.setCargo(cargo);
        historicoUsuario.setUsuario(usuario);
        historicoUsuarioService.insereHistoricoUsuario(historicoUsuario);
        return ResponseEntity.ok().build();
    }


    @PutMapping(value = "/usuarios/{id}")
    public ResponseEntity<String> atualizaUsuario (@PathVariable Integer id, @Valid @RequestBody UsuarioDTO dto) {
        usuarioService.updateUsuario(id, dto);
        Date dt = new Date();
        Usuario usuario = usuarioService.findById(id);
        //Cargo cargo = cargoMapper.toEntity(dto.getCargo());
        //historicoUsuarioService.alteraDataFinal(id, dt);
        historicoUsuario.setData_inicio(dt);
        historicoUsuario.setData_final(dt);
       // historicoUsuario.setCargo(cargo);
        historicoUsuario.setUsuario(usuario);
        //historicoUsuarioService.insereHistoricoUsuario(historicoUsuario);
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
package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.service.OrdemFornecimentoService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin
public class OrdemFornecimentoController {

    @Autowired
    OrdemFornecimentoService ordemFornecimentoService;

    @GetMapping("/ordem-fornecimento/{id}/usuarios")
    public ResponseEntity<List<Integer>> getUsuariosOf(@PathVariable int id) {
        List<Integer> usuariosOf = ordemFornecimentoService.getUsuariosOf(id);
        return ResponseEntity.ok().body(usuariosOf);
    }

    @GetMapping("/ordem-fornecimento/{id}/situacao")
    public ResponseEntity<HashMap<String, Object>> getSituacaoOf(@PathVariable int id) {
        HashMap<String, Object> sitOf = ordemFornecimentoService.getSituacaoOf(id);
        return ResponseEntity.ok().body(sitOf);
    }

    @GetMapping("/ordens-fornecimento")
    public List<HashMap<String, Object>> getOrdemDeFornecimento() {
        return new OrdemFornecimentoService().getOrdemDeFornecimento();
    }

    @GetMapping("/ordem-fornecimento/{id}")
    public OrdemFornecimento getOrdemDeFornecimento(@PathVariable int id) {
        return new OrdemFornecimentoService().getOrdemDeFornecimento(id);
    }

    @PostMapping(value = "/ordem-fornecimento/usuario-situacao")
    public ResponseEntity<String> setSituacao(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        int situacao = json.getInt("sit");
        int of = json.getInt("of");
        String ref = json.getString("ref");
        ArrayList<Integer> listaUsu = new ArrayList<Integer>();
        JSONArray usuarios = json.getJSONArray("usu");
        for (int i = 0; i < usuarios.length(); i++) {
            listaUsu.add(usuarios.getInt(i));
        }
        ordemFornecimentoService.registraUsuSit(listaUsu, situacao, of, ref);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("ordens-fornecimento/usuario/{id}")
    public List<HashMap<String, Object>> getOrdensFornUsu(@PathVariable int id) {
        return ordemFornecimentoService.getOrdensFornUsuario(id);
    }
}





















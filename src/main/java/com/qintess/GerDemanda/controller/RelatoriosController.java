package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.service.RelatoriosService;
import com.qintess.GerDemanda.service.dto.RelatorioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
public class RelatoriosController {

    @Autowired
    RelatoriosService relatoriosService;

    @GetMapping("/relatorio-orcamento/{idOf}")
    public ResponseEntity<HashMap<String, Object>> getRelatorioOrcamento (@PathVariable int idOf){
        RelatoriosService rs = new RelatoriosService();
        HashMap<String, Object> response = rs.getRelatorioOrcamento(idOf);

        return response.isEmpty() ?
                new ResponseEntity<HashMap<String, Object>>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(response);
    }

    @GetMapping("/relatorio-entrega/{idOf}")
    public ResponseEntity<HashMap<String, Object>> getRelatorioEntrega (@PathVariable int idOf){
        RelatoriosService rs = new RelatoriosService();
        HashMap<String, Object> response = rs.getRelatorioEntrega(idOf);

        return response.isEmpty() ?
                new ResponseEntity<HashMap<String, Object>>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(response);
    }

    @GetMapping("/relatorio-orcamento/xlsx/{idOf}")
    public ResponseEntity<byte[]> getRelatorioOrcamentoXlsx (@PathVariable int idOf){
        RelatoriosService rs = new RelatoriosService();
        byte[] response = rs.getRelatorioOrcamentoXlsx(idOf);

        String fileName = "relatorioorcamento" + Integer.toString(idOf) + ".xlsx";
        HttpHeaders header = new HttpHeaders();

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileName).build();
        header.setContentDisposition(contentDisposition);

        return response.length == 0 ?
                new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok()
                        .headers(header)
                        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                        .body(response);
    }

    @GetMapping("/relatorio-entrega/xlsx/{idOf}")
    public ResponseEntity<byte[]> getRelatorioEntregaXlsx (@PathVariable int idOf){
        RelatoriosService rs = new RelatoriosService();
        byte[] response = rs.getRelatorioEntregaXlsx(idOf);

        String fileName = "relatorioentrega" + Integer.toString(idOf) + ".xlsx";
        HttpHeaders header = new HttpHeaders();

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileName).build();
        header.setContentDisposition(contentDisposition);

        return response.length == 0 ?
                new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok()
                        .headers(header)
                        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                        .body(response);
    }

    @GetMapping("/relatorio-orcamento/txt/{idOf}")
    public ResponseEntity<byte[]> getRelatorioOrcamentoTxt (@PathVariable int idOf){
        RelatoriosService rs = new RelatoriosService();
        byte[] response = rs.getRelatorioOrcamentoTxt(idOf);

        String fileName = "relatorioorcamento" + Integer.toString(idOf) + ".txt";
        HttpHeaders header = new HttpHeaders();

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileName).build();
        header.setContentDisposition(contentDisposition);

        return response.length == 0 ?
                new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok()
                        .headers(header)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(response);
    }

    @GetMapping("/relatorio-entrega/txt/{idOf}")
    public ResponseEntity<byte[]> getRelatorioEntregaTxt (@PathVariable int idOf){
        RelatoriosService rs = new RelatoriosService();
        byte[] response = rs.getRelatorioEntregaTxt(idOf);

        String fileName = "relatorioentrega" + Integer.toString(idOf) + ".txt";
        HttpHeaders header = new HttpHeaders();

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileName).build();
        header.setContentDisposition(contentDisposition);

        return response.length == 0 ?
                new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok()
                        .headers(header)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(response);
    }

    @GetMapping("relatorio-novo")
    ResponseEntity<List<RelatorioDTO>> getRelatorioNovo() {
        List<RelatorioDTO> relatorionovo = relatoriosService.getRelatorioNovo();
        return (relatorionovo == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(relatorionovo);
    }

    @GetMapping("/relatorio-novo/xlsx")
    public ResponseEntity<byte[]> getRelatorioNovoXlsx (){
        byte[] response = relatoriosService.getRelatoriosXlsx();

        String fileName = "relatorio-novo.xlsx";
        HttpHeaders header = new HttpHeaders();

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileName).build();
        header.setContentDisposition(contentDisposition);

        return response.length == 0 ?
                new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok()
                        .headers(header)
                        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                        .body(response);
    }

}

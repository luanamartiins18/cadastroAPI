package com.qintess.GerDemanda.controller;

import com.qintess.GerDemanda.model.Candidatos;
import com.qintess.GerDemanda.model.Curriculo;
import com.qintess.GerDemanda.model.HistoricoCandidato;
import com.qintess.GerDemanda.model.StatusCandidato;
import com.qintess.GerDemanda.service.CandidatosService;
import com.qintess.GerDemanda.service.HistoricoCandidatoService;
import com.qintess.GerDemanda.service.VagasService;
import com.qintess.GerDemanda.service.dto.CandidatosDTO;
import com.qintess.GerDemanda.service.dto.HistoricoCandidatoDTO;
import com.qintess.GerDemanda.service.mapper.CandidatosMapper;
import com.qintess.GerDemanda.service.mapper.StatusCandidatoMapper;
import com.qintess.GerDemanda.service.repositories.CandidatosRepository;
import com.qintess.GerDemanda.service.repositories.CurriculoRepository;
import com.qintess.GerDemanda.service.repositories.VagasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Controller
@RestController
@CrossOrigin
@Slf4j
public class CandidatosController {

    @Autowired
    HistoricoCandidato historicoCandidato;

    @Autowired
    StatusCandidatoMapper statusCandidatoMapper;

    @Autowired
    CandidatosMapper candidatoMapper;

    @Autowired
    CandidatosService candidatoService;

    @Autowired
    HistoricoCandidatoService historicoCandidatoService;

    @Autowired
    VagasService vagasService;

    @Autowired
    VagasRepository vagasRepository;

    @Autowired
    CurriculoRepository curriculoRepository;

    @Autowired
    CandidatosRepository candidatosRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Salvar arquivo no diretório local
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path path = Paths.get("C:\\Users\\Qintess\\cadastroControl\\cadastroAPI\\src\\main\\uploads\\" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Após ter salvo o documento no diretorio, esse codigo abaixo salva link no table de curriculo
            Curriculo arquivo = new Curriculo();
            String fileUrl = "http://192.168.2.55:4200/download/" + fileName; // mudança aqui
            arquivo.setLink(fileUrl);
            curriculoRepository.save(arquivo);

            // retorna um redirecionamento para o endpoint de download
            URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/")
                    .path(fileName).build().toUri();
            return ResponseEntity.created(location).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Obter o caminho completo do arquivo
        Path filePath = Paths.get("C:\\Users\\Qintess\\cadastroControl\\cadastroAPI\\src\\main\\uploads\\" + fileName);
        // Criar um objeto Resource
        Resource resource = new FileSystemResource(filePath);
        // Obter o tipo MIME do arquivo
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // Tratar erro
        }
        // Retornar o arquivo
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @PostMapping(value = "/candidatos")
    public ResponseEntity<String> insereCandidatos(@Valid @RequestBody CandidatosDTO dto) {
        candidatoService.insereCandidatos(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/candidatos/{id}")
    public ResponseEntity<String> atualizaCandidatos (@PathVariable Integer id, @Valid @RequestBody CandidatosDTO dto) {
       Candidatos candidatos = candidatosRepository.findFirstById(dto.getId());
        candidatoService.updateCandidatos(id, dto);
        StatusCandidato statusCandidato = statusCandidatoMapper.toEntity(dto.getStatus_candidato());
        Date dt = new Date();
        //Atualizar historico anterior
        HistoricoCandidato historico = historicoCandidatoService.findUltimoHistoricoByCandidato(candidatos.getId());
        if(historico != null) {
            historicoCandidatoService.updateUltimoHistoricoCandidato(dt, "Não", historico.getId());
        }
        //Insere novo historico
        historicoCandidato.setData_inicio(dt);
        historicoCandidato.setStatus_candidato(statusCandidato);
        historicoCandidato.setVigente("Sim");
        historicoCandidato.setCandidatos(candidatos);
        historicoCandidatoService.insereHistoricoCandidato(historicoCandidato);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/candidatosvaga/{id}")
    ResponseEntity<List<CandidatosDTO>> getListaCandidatosPorVagas(@PathVariable Integer id ){
        List<CandidatosDTO> listacandidato = candidatoService.getListaCandidatosPorVaga(id);
        return (listacandidato.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listacandidato);
    }


    @GetMapping("/candidatos/{id}")
    ResponseEntity<CandidatosDTO> getCandidatosId(@PathVariable Integer id) {
        CandidatosDTO candidatos = candidatoService.findByIdDTO(id);
        return Objects.isNull(candidatos) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(candidatos);
    }

    @GetMapping("/candidatos")
    ResponseEntity<List<CandidatosDTO>> getListaCandidatos() {
        List<CandidatosDTO> listaCandidatos = candidatoService.getListaCandidatos();
        return (listaCandidatos.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaCandidatos);
    }

    @GetMapping("/candidatosdisponivel")
    ResponseEntity<List<CandidatosDTO>> getListaCandidatosDisponivel() {
        List<CandidatosDTO> listaCandidatos = candidatoService.getListaCandidatoDisponivel();
        return (listaCandidatos.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listaCandidatos);
    }


    @GetMapping("/candidatosstatus/{idStatus}")
    ResponseEntity<List<CandidatosDTO>> getListaCandidatoPorStatus(@PathVariable Integer idStatus) {
        List<CandidatosDTO> listausuario = candidatoService.getListaCandidatoPorStatus(idStatus);
        return (listausuario.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listausuario);
    }

    @PutMapping(value = "/desvincular/{id}")
    public ResponseEntity<String> desvincularCandidato (@PathVariable Integer id) {
        candidatoService.desvincularCandidato(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/historicocandidatos/{id}")
    ResponseEntity<List<HistoricoCandidatoDTO>> getListaHistoricoCandidatoComId(@PathVariable Integer id) {
        Candidatos candidatos = candidatoMapper.toEntity(candidatoService.getCandidatoById(id));
        List<HistoricoCandidatoDTO> listahistoricoCandidato = historicoCandidatoService.findByCandiatoOrderByDataInicioDesc(candidatos.getId());
        return (listahistoricoCandidato.size() == 0) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(listahistoricoCandidato);
    }

}

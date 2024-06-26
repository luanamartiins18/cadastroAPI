package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.*;
import com.qintess.GerDemanda.service.dto.*;
import com.qintess.GerDemanda.service.mapper.*;
import com.qintess.GerDemanda.service.repositories.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    public static final String STATUS_ATIVO_DESCRICAO = "Ativo";
    public static final int CARGO_COLABORADOR = 3;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TipoRepository tipoRepository;

    @Autowired
    BuRepository buRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    HistoricoRepository historicoRepository;

    @Autowired
    HistoricoPerfilRepository historicoPerfilRepository;

    @Autowired
    HistoricoOperacaoRepository historicoOperacaoRepository;

    @Autowired
    HistoricoMaquinasRepository historicoMaquinasRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private HistoricoUsuarioMapper historicoMapper;

    @Autowired
    private HistoricoPerfilMapper historicoPerfilMapper;

    @Autowired
    private HistoricoOperacaoMapper historicoOperacaoMapper;

    @Autowired
    private HistoricoMaquinasMapper historicoMaquinasMapper;

    @Autowired
    private ModeloMapper modeloMapper;

    @Autowired
    private EquipamentoMapper equipamentoMapper;

    @Autowired
    private MemoriaMapper memoriaMapper;

    @Autowired
    private PerfilMapper perfilMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;


    public LoginDTO checkUsuario(LoginDTO dto) {
        LoginDTO usuarioResumidoDTO = loginMapper.toDto(
                this.usuarioRepository.findFirstByCodigoReAndSenha(dto.getCodigoRe(), dto.getSenha()));
        // Verificar o primeiro acesso do usuário
        validaStatus(usuarioResumidoDTO);
        return usuarioResumidoDTO;
    }

    public UsuarioDTO newUsuarioMapper(Usuario usuario) {
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);
        usuarioDTO.setTag(usuario.getTag());
        usuarioDTO.setPatrimonio(usuario.getPatrimonio());
        return usuarioDTO;
    }

    public UsuarioDTO getUsuarioByRe(String re) {
        return this.newUsuarioMapper(usuarioRepository.findFirstByCodigoRe(re));
    }

    public UsuarioDTO getUsuarioByRE(String re, Integer id) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByCodigoReAndIdNot(re, id));
    }

    public UsuarioDTO getUsuarioByEmail(String email, Integer id) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByEmailAndIdNot(email, id));
    }

    //public UsuarioDTO getUsuarioByCpf(String cpf, Integer id) {
     //   return usuarioMapper.toDto(usuarioRepository.findFirstByCpfAndIdNot(cpf, id));
  //  }


    public List<Usuario> getUsuariosAtivos() {
        return usuarioRepository.findByStatusAndCargoIdOrderByNomeAsc(STATUS_ATIVO_DESCRICAO, CARGO_COLABORADOR);
    }

    public List<UsuarioDTO> getUsuariosAtivosDTO() {
        return usuarioMapper.toDto(this.getUsuariosAtivos());
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", Usuario.class.getName()));
    }

    public HistoricoMaquinas findByIdHistorico(Integer id) {
        return historicoMaquinasRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", HistoricoMaquinas.class.getName()));
    }

    public UsuarioDTO findByIdDTO(Integer id) {
        return usuarioToDTO(this.findById(id));
    }

    public HistoricoMaquinasDTO findByIdHistoricoDTO(Integer id) {
        return maquinasToDTO(this.findByIdHistorico(id));
    }

    public List<UsuarioDTO> getListaUsuarios() {
        return usuarioRepository.findByOrderByNomeAsc().stream().map(obj -> usuarioToDTO(obj)).collect(Collectors.toList());
    }

    public List<UsuarioDTO> getListaUsuariosPorOperacao(Integer idContrato) {
        return usuarioRepository.listarUsuarioPorOperacao(idContrato).stream().map(obj -> usuarioToDTO(obj)).collect(Collectors.toList());
    }

    public List<HistoricoUsuarioDTO> getListaHistorico() {
        return historicoRepository.findAllByOrderByDataInicioDesc().stream().map(obj -> historicoToDTO(obj)).collect(Collectors.toList());
    }

    public List<HistoricoPerfilDTO> getListaHistoricoPerfil() {
        return historicoPerfilRepository.findAllByOrderByDataInicioDesc().stream().map(obj -> historicoPerfilToDTO(obj)).collect(Collectors.toList());
    }

    public List<HistoricoOperacaoDTO> getListaHistoricoOperacao() {
        return historicoOperacaoRepository.findAllByOrderByDataInicioDesc().stream().map(obj -> historicoOperacaoToDTO(obj)).collect(Collectors.toList());
    }

    public List<HistoricoMaquinasDTO> getListaHistoricoMaquinas() {
        return historicoMaquinasRepository.findAllByOrderByDataInicioDesc().stream().map(obj -> historicoMaquinasToDTO(obj)).collect(Collectors.toList());
    }

    public UsuarioDTO getUsuarioByCelular(String celular, Integer id) {
        return usuarioMapper.toDto(usuarioRepository.findFirstByCelularAndIdNot(celular, id));
    }

    public void createPasswordResetTokenForUser(Usuario usuario, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, usuario);
        tokenRepository.save(myToken);
    }

    @Transactional
    public void
    insereUsuario(UsuarioDTO dto) {
        // validacao(dto);
        Usuario obj = usuarioMapper.toEntity(dto);
        obj.setData_inicio(dto.getData_inicio());
        obj.setNome(obj.getNome().toUpperCase());
        obj.setStatus(STATUS_ATIVO_DESCRICAO);
        obj.setData_final(null);
        obj.setSenha(DigestUtils.sha256Hex(dto.getNome()));
        obj.setPrimeiroAcesso(true);
        usuarioRepository.save(obj);
    }

    //  private void validacao(UsuarioDTO dto) {
        //   Integer id = Objects.isNull(dto.getId()) ? 0 : dto.getId();
        //   if (Objects.nonNull(getUsuarioByCpf(dto.getCpf(), id))) {
            //        throw new ValidationException("O CPF já está em uso");
            //    }
        //    if (Objects.nonNull(getUsuarioByEmail(dto.getEmail(), id))) {
        //         throw new ValidationException("O e-mail já está em uso");
            //    }
        //    if (Objects.nonNull(getUsuarioByRE(dto.getCodigoRe(), id))) {
            //       throw new ValidationException("O códigoRe já está em uso");
     //   }
    //}

    private void validaStatus(LoginDTO dto) {
        if (Objects.isNull(dto)) {
            throw new RuntimeException("Usuário ou senha inválida");
        }
        if (!dto.getStatus().equals(STATUS_ATIVO_DESCRICAO)) {
            throw new RuntimeException("Sua conta está desativada!");
        }
    }

    @Transactional
    public void updateUsuario(Integer id, UsuarioDTO dto) {
        dto.setId(id);
        // validacao(dto);
        Usuario objOld = findById(id);
        usuarioMapperUpdate(dto, objOld);
        usuarioRepository.save(objOld);
    }

    @Transactional
    public void updateSenhaUsuario(Integer id, UsuarioDTO dto) {
        dto.setId(id);
        //  validacao(dto);
        Usuario objOld = findById(id);
        usuarioSenhaMapperUpdate(dto, objOld);
        usuarioRepository.save(objOld);
    }


    @Transactional
    public void atualizaFuncao(Integer idUsuario, FuncaoDTO dto) {
        usuarioRepository.updateFuncao(dto.getCargo().getId(), dto.getBu().getId(), dto.getTipo().getId(),idUsuario);
    }

    @Transactional
    public void atualizaPerfil(Integer idUsuario, PerfilHDTO dto) {
        usuarioRepository.updatePerfil(dto.getPerfil().getId(), idUsuario);
    }

    @Transactional
    public void atualizaContrato(Integer idUsuario, ContratoHDTO dto) {
        usuarioRepository.updateContrato(dto.getContrato().getId(), idUsuario);
    }

    @Transactional
    public void atualizaMaquinas(Integer idUsuario, MaquinasDTO dto) {
        usuarioRepository.updateMaquinas(dto.getModelo().getId(), dto.getEquipamento().getId(), dto.getMemoria().getId(), dto.getTag(), dto.getPatrimonio(), idUsuario);
    }

    private void usuarioMapperUpdate(UsuarioDTO dto, Usuario objOld) {
        Usuario obj = usuarioRepository.findByCpf(dto.getCpf());
        Usuario objNew = usuarioMapper.toEntity(dto);
        objOld.setNome(objNew.getNome().toUpperCase());
        objOld.setCpf(objNew.getCpf());
        objOld.setRg(objNew.getRg());
        objOld.setOrg_emissor(objNew.getOrg_emissor());
        objOld.setData_emissao(objNew.getData_emissao());
        objOld.setData_nascimento(objNew.getData_nascimento());
        objOld.setEndereco(objNew.getEndereco());
        objOld.setNumero(objNew.getNumero());
        objOld.setComplemento(objNew.getComplemento());
        objOld.setCep(objNew.getCep());
        objOld.setCelular(objNew.getCelular());
        objOld.setEmail(objNew.getEmail());
        objOld.setEmail_pessoal(objNew.getEmail_pessoal());
        objOld.setCodigoRe(objNew.getCodigoRe());
        objOld.setCargo(objNew.getCargo());
        objOld.setCidade(objNew.getCidade());
        objOld.setUf(objNew.getUf());
        objOld.setTipo(objNew.getTipo());
        objOld.setBu(objNew.getBu());
        objOld.setContrato(objNew.getContrato());
        objOld.setEquipamento(objNew.getEquipamento());
        objOld.setModelo(objNew.getModelo());
        objOld.setMemoria(objNew.getMemoria());
        objOld.setTag(objNew.getTag());
        objOld.setPatrimonio(objNew.getPatrimonio());
        objOld.setPerfil(objNew.getPerfil());
        objOld.setData_inicio(objNew.getData_inicio());
        objOld.setData_final(objNew.getData_final());
        if (!dto.getSenha().isEmpty() && !dto.getSenha().equals(obj.getSenha())) {
            obj.setSenha(DigestUtils.sha256Hex(dto.getSenha()));
        }
        objOld.setPrimeiroAcesso(false);
        usuarioRepository.save(objOld);


    }

    private void usuarioSenhaMapperUpdate(UsuarioDTO dto, Usuario objOld) {
        Usuario obj = usuarioRepository.findByCpf(dto.getCpf());
        if (!dto.getSenha().isEmpty() && !dto.getSenha().equals(obj.getSenha())) {
            obj.setSenha(DigestUtils.sha256Hex(dto.getSenha()));
        }
        objOld.setPrimeiroAcesso(false);
        usuarioRepository.save(objOld);
    }

        public void alteraStatus(Integer id, String acao) {
        Usuario usuario = this.findById(id);
        if(acao.equals(("Ativo")));
            usuario.setStatus("Ativo");
        if (acao.equals("desativar")) {
            usuario.setStatus("Desligado");
        }
        this.usuarioRepository.saveAndFlush(usuario);
    }

    private UsuarioDTO usuarioToDTO(Usuario obj) {
        UsuarioDTO dto = usuarioMapper.toDto(obj);
        dto.setData_final(obj.getData_final());
        dto.setData_inicio(obj.getData_inicio());
        dto.setPatrimonio(obj.getPatrimonio());
        dto.setTag(obj.getTag());
        return dto;
    }

    private HistoricoMaquinasDTO maquinasToDTO(HistoricoMaquinas obj) {
        HistoricoMaquinasDTO dto = historicoMaquinasMapper.toDto(obj);
        ModeloDTO modelo = modeloMapper.toDto(obj.getModelo());
        EquipamentoDTO equipamento = equipamentoMapper.toDto(obj.getEquipamento());
        MemoriaDTO memoria = memoriaMapper.toDto(obj.getMemoria());
        dto.setTag(obj.getTag());
        dto.setPatrimonio(obj.getPatrimonio());
        dto.setEquipamento(equipamento);
        dto.setModelo(modelo);
        dto.setMemoria(memoria);
        dto.setData_inicio(obj.getData_inicio());
        dto.setData_final(obj.getData_final());
        return dto;
    }

    private HistoricoUsuarioDTO historicoToDTO(HistoricoUsuario obj) {
        HistoricoUsuarioDTO dto = historicoMapper.toDto(obj);
        dto.setVigente(obj.getVigente());
        return dto;
    }

    private HistoricoPerfilDTO historicoPerfilToDTO(HistoricoPerfil obj) {
        HistoricoPerfilDTO dto = historicoPerfilMapper.toDto(obj);
        PerfilDTO perfil = perfilMapper.toDto(obj.getPerfil());
        dto.setVigente(obj.getVigente());
        dto.setPerfil(perfil);
        return dto;
    }

    private HistoricoOperacaoDTO historicoOperacaoToDTO(HistoricoOperacao obj) {
        HistoricoOperacaoDTO dto = historicoOperacaoMapper.toDto(obj);
        dto.setVigente(obj.getVigente());
        return dto;
    }
    private HistoricoMaquinasDTO historicoMaquinasToDTO(HistoricoMaquinas obj) {
        HistoricoMaquinasDTO dto = historicoMaquinasMapper.toDto(obj);
        MemoriaDTO memoria = memoriaMapper.toDto(obj.getMemoria());
        dto.setVigente(obj.getVigente());
        dto.setPatrimonio(obj.getPatrimonio());
        dto.setTag(obj.getTag());
        dto.setMemoria(memoria);
        return dto;
    }
}
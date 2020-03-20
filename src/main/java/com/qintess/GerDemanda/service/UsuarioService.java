package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Usuario;
import com.qintess.GerDemanda.model.UsuarioPerfil;
import com.qintess.GerDemanda.repositories.UsuarioPerfilRepository;
import com.qintess.GerDemanda.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UsuarioService {

	public static final int STATUS_ATIVO_CODIGO = 1;
	public static final String STATUS_ATIVO_DESCRICAO = "Ativo";
	public static final int CARGO_COLABORADOR = 3;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	UsuarioPerfilRepository usuarioPerfilRepository;

	public HashMap<String, Object> getPerfilUsuario(int idUsu) {
		UsuarioPerfil usuarioPerfil = usuarioPerfilRepository.findByUsuarioPerfilAndStatus(idUsu, STATUS_ATIVO_CODIGO);
		HashMap<String, Object> perfil = new HashMap<String, Object>();
		perfil.put("descricao", usuarioPerfil.getPerfil());
		return perfil;

	}

	public List<Usuario> getUsuariosAtivos() {
		return usuarioRepository.findByStatusAndCargoIdOrderByNomeAsc(STATUS_ATIVO_DESCRICAO, CARGO_COLABORADOR);
	}

	public List<Usuario> getUsuarioBySigla(int id) {
		return usuarioRepository.findByStatusAndCargoIdAndListaSiglasIdInOrderByNomeAsc(STATUS_ATIVO_DESCRICAO, CARGO_COLABORADOR, id);
	}

	public Usuario getUsuarioByRe(String re) {
		return usuarioRepository.findByCodigoRe(re).get(0);
	}

	public boolean checkUsuario(String re, String senha) {
		return this.usuarioRepository.existsByCodigoReAndSenha(re, senha);
	}
}
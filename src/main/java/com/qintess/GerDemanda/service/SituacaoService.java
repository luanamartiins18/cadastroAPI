package com.qintess.GerDemanda.service;
import java.util.List;

import com.qintess.GerDemanda.model.Situacao;
import com.qintess.GerDemanda.repositories.SituacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SituacaoService {

	@Autowired
	SituacaoRepository situacaoRepository;
	
	public List<Situacao> getSituacao() {
		return situacaoRepository.findByOrderByDescricaoAsc();
	}
}

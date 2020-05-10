package com.qintess.GerDemanda.service.dto;

import com.qintess.GerDemanda.model.TarefaOf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelatorioDTO {

    String numero_of;
    String colaborador;
    String status_of;
    Double valor_ustibb;
    Double valor;
    String referencia;
    String sigla;
    Long qtd;

    public RelatorioDTO(
            String numero_of,
            String colaborador,
            String status_of,
            Double valor_ustibb,
            String referencia,
            String sigla,
            Long qtd
    ) {
        this.numero_of = numero_of;
        this.colaborador = colaborador;
        this.status_of = status_of;
        this.valor_ustibb = valor_ustibb;
        this.referencia = referencia;
        this.sigla = sigla;
        this.qtd = qtd;
    }

    public RelatorioDTO(
            String numero_of,
            String colaborador,
            String status_of,
            Double valor_ustibb,
            String referencia,
            String sigla
    ) {
        this.numero_of = numero_of;
        this.colaborador = colaborador;
        this.status_of = status_of;
        this.valor_ustibb = valor_ustibb;
        this.referencia = referencia;
        this.sigla = sigla;
    }


    public RelatorioDTO(
            String numero_of,
            String status_of,
            Double valor_ustibb,
            String referencia,
            String sigla

    ) {
        this.numero_of = numero_of;
        this.status_of = status_of;
        this.valor_ustibb = valor_ustibb;
        this.referencia = referencia;
        this.sigla = sigla;
    }


    public RelatorioDTO(
            TarefaOf tarefaOf

    ) {

        this.numero_of = tarefaOf.getUsuarioOrdemFornecimento().getOrdemFornecimento().getNumeroOFGenti();
        this.colaborador = tarefaOf.getUsuarioOrdemFornecimento().getUsuario().getNome();
        this.status_of = tarefaOf.getUsuarioOrdemFornecimento().getOrdemFornecimento().getSituacaoUsu().getDescricao();
        this.valor_ustibb = tarefaOf.getItemGuia().getValor();
        this.referencia = tarefaOf.getUsuarioOrdemFornecimento().getOrdemFornecimento().getReferencia();
        this.sigla =tarefaOf.getUsuarioOrdemFornecimento().getOrdemFornecimento().getSigla().getDescricao();
        this.qtd = tarefaOf.getQuantidade();
    }
}
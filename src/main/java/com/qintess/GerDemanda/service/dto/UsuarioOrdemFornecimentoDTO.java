package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.model.Usuario;
import lombok.*;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioOrdemFornecimentoDTO {
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar dtCriacao;
    private Calendar dtExclusao;
    private int status;
    private Usuario usuario;
    private OrdemFornecimento ordemFornecimento;
}

package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qintess.GerDemanda.model.OrdemFornecimento;
import com.qintess.GerDemanda.model.Usuario;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioOrdemFornecimentoDTO {
    private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtCriacao;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dtExclusao;
    private int status;
    private UsuarioDTO usuario;
}

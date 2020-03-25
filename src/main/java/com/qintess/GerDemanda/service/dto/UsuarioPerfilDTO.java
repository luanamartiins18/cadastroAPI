package com.qintess.GerDemanda.service.dto;

import lombok.*;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioPerfilDTO {
    private int id;
    private Calendar dtCriacao;
    private Calendar dtExclusao;
    private int status;
    private PerfilDTO perfil;
}















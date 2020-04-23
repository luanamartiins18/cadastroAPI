package com.qintess.GerDemanda.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensagemInDTO {
    private String corpo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtExp;
    private Integer idResp;
    private String titulo;
    private Integer idSigla;
}
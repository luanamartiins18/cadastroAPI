package com.qintess.GerDemanda.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class HistoricoPropostaDTO {
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_final;

    private String vigente;

    private PropostaDTO proposta;

    private CandidatosDTO candidatos;

    private StatusCandidatoDTO status_candidatos;

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public String getVigente() {
        return vigente;
    }
}

package com.qintess.GerDemanda.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class HistoricoMaquinasDTO {
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_final;

    private String vigente;

    @NotNull(message = "O campo usuario é obrigatório!")
    private UsuarioDTO usuario;

    @NotNull(message = "o campo modelo é obrigatorio!")
    private ModeloDTO modelo;


    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public String getVigente() {
        return vigente;
    }

}

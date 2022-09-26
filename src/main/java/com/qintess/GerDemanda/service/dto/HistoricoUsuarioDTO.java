package com.qintess.GerDemanda.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.springframework.stereotype.Component;


import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class HistoricoUsuarioDTO {
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data_final;

    private String vigente;

    @NotNull(message = "O campo usuario é obrigatório!")
    private UsuarioDTO usuario;

    @NotNull(message = "O campo cargo é obrigatório!")
    private CargoDTO cargo;

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public String getVigente() {
        return vigente;
    }
}

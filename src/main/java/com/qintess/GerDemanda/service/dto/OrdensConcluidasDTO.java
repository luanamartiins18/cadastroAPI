package com.qintess.GerDemanda.service.dto;

public class OrdensConcluidasDTO {
    String referencia;
    Integer num_of;
    String sigla;
    String responsavel_t;
    String gerente_t;
    Double ustibb;
    Double valor;
    String status;

    public OrdensConcluidasDTO(Object[] obj) {
        this.referencia = (String) obj[0];
        this.num_of = Integer.parseInt((String) obj[1]);
        this.sigla = (String) obj[2];
        this.responsavel_t = (String) obj[3];
        this.gerente_t = (String) obj[4];
        this.ustibb = (Double) obj[5];
        this.valor = (Double) obj[6];
        this.status = (String) obj[7];
    }

    public OrdensConcluidasDTO() {
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getNum_of() {
        return num_of;
    }

    public void setNum_of(Integer num_of) {
        this.num_of = num_of;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getResponsavel_t() {
        return responsavel_t;
    }

    public void setResponsavel_t(String responsavel_t) {
        this.responsavel_t = responsavel_t;
    }

    public String getGerente_t() {
        return gerente_t;
    }

    public void setGerente_t(String gerente_t) {
        this.gerente_t = gerente_t;
    }

    public Double getUstibb() {
        return ustibb;
    }

    public void setUstibb(Double ustibb) {
        this.ustibb = ustibb;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

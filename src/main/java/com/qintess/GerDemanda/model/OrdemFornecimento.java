package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@Entity(name = "OrdemFornecimento")
@Table(name = "ordem_forn")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdemFornecimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;
	
	@Column(name = "numero_OF")
	private String numeroOF;
	
	@Column(name = "numero_OF_genti")
	private String numeroOFGenti;
	
	@Column
	private String fabrica;
	
	@Column
	private String tema;
	
	@Column
	private boolean agil;
	
	@Column(name = "usti_bb")
	private double ustiBB;
	
	@Column
	private String uor;
	
	@Column
	private String demanda;
	
	@Column
	private String acao;
	
	@Column
	private String tipo;
	
	@Column(name = "cd_ti")
	private String cdTI;
	
	@Column(name = "dt_abertura")
	@Temporal(TemporalType.DATE)
	private Calendar dtAbertura;
	
	@Column(name = "dt_previsao")
	@Temporal(TemporalType.DATE)
	private Calendar dtPrevisao;
	
	@Column(name = "dt_entrega")
	@Temporal(TemporalType.DATE)
	private Calendar dtEntrega;
	
	@Column(name = "dt_devolvida")
	@Temporal(TemporalType.DATE)
	private Calendar dtDevolvida;
	
	@Column(name = "dt_recusa")
	@Temporal(TemporalType.DATE)
	private Calendar dtRecusa;
	
	@Column(name = "dt_aceite")
	@Temporal(TemporalType.DATE)
	private Calendar dtAceite;
	
	@Column(name = "responsavel_t")
	private String responsavelTecnico;
	
	@Column(name = "gerente_t")
	private String gerenteTecnico;

	@JsonBackReference
	@ManyToOne  
	@JoinColumn(name = "fk_sigla")
	private Sigla sigla;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_situacao_genti")
	private Situacao situacaoGenti;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_situacao_usu")
	private Situacao situacaoUsu;
}





























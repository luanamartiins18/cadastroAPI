package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "usuario_x_mensagem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioMensagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_leitura")
	private Date dtLeitura;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_usuario", nullable=false)
	@NotNull
	private Usuario usuarioMens;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "fk_mensagem", nullable=false)
	@NotNull
	private Mensagem mensagem;

	public UsuarioMensagem(Usuario usuario, Mensagem mensagem) {
		this.usuarioMens = usuario;
		this.mensagem = mensagem;
	}
}


























































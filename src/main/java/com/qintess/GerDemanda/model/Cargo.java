package com.qintess.GerDemanda.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cargo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;
	private String descricao;

	@JsonManagedReference
	@OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
	private List<Usuario> usuarios;
}
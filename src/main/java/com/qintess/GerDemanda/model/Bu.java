package com.qintess.GerDemanda.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "bu")
public class Bu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    private String descricao;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "bu", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
}

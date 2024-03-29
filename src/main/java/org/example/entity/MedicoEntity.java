package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Medico")
@Table(name = "Medicos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MedicoEntity {

    @Id
    private String crm;
    private String nome;
    private String especialidade;
    @ManyToMany
    private List<PacienteEntity> pacientes;
}

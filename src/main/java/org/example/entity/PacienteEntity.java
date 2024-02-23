package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Paciente")
@Table(name = "Pacientes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PacienteEntity {

    @Id
    private String cpf;
    private String nome;
    private Integer idade;
    private String email;
}

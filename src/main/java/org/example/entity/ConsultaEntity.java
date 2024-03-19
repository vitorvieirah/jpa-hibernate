package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Consulta")
@Table(name = "Consultas")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private PacienteEntity paciente;
    @ManyToOne
    private MedicoEntity medico;
    private LocalDate data;
}

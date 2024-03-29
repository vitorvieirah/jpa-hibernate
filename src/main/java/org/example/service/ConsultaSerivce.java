package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.ConsultaDao;
import org.example.dao.MedicoDao;
import org.example.dao.PacienteDao;
import org.example.domain.Consulta;
import org.example.domain.Medico;
import org.example.domain.Paciente;
import org.example.exception.ConsultaDataBaseException;
import org.example.exception.MedicoDataBaseException;
import org.example.exception.PacienteDataBaseException;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
public class ConsultaSerivce {

    private final PacienteDao pacienteDao;
    private final MedicoDao medicoDao;
    private final ConsultaDao consultaDao;

    public String marcar(String crm, String cpfPaciente, EntityManager em) throws MedicoDataBaseException, PacienteDataBaseException, ConsultaDataBaseException {
        List<Consulta> consultas =  consultaDao.buscarConsultasPorMedico(crm, em);

        LocalDate dataConsulta = buscarDataMaisRecente(consultas);

        Optional<Medico> medico = medicoDao.consultarPorCrm(crm,em);
        Optional<Paciente> paciente = pacienteDao.buscarPorCpf(cpfPaciente, em);

        validaOptional(Optional.ofNullable(paciente), 1);
        validaOptional(Optional.ofNullable(medico), 2);

        if(validaData(dataConsulta)){
            consultaDao.salvar(Consulta.builder()
                    .paciente(paciente.get())
                    .medico(medico.get())
                    .data(dataConsulta)
                    .build(), em);
        }else
            throw new RuntimeException("Data indisponível");

        return "Consulta do " + paciente.get().getNome() + " marcada com o médico " + medico.get().getNome() +
                " na data " + formataData(dataConsulta);
    }

    public Consulta buscarConsultaPorId(Long id, EntityManager em) throws ConsultaDataBaseException {
        Optional<Consulta> consulta = consultaDao.buscarPorId(id, em);

        if(consulta.isEmpty())
            throw new RuntimeException("Consulta não encotrada");
        else
            return consulta.get();
    }

    public void cancelar(Long idConsulta, EntityManager em) throws ConsultaDataBaseException {
        consultaDao.deletar(idConsulta, em);
    }

    public String remarcar(Long idConsulta, LocalDate novaData, EntityManager em) throws ConsultaDataBaseException {
        Optional<Consulta> consultaOptional = consultaDao.buscarPorId(idConsulta, em);
        Consulta consulta;

        if(consultaOptional.isPresent())
            consulta = consultaOptional.get();
        else
            throw new RuntimeException("Consulta não encontrada");

        List<Consulta> consultas = consultaDao.buscarConsultasPorMedico(consulta.getMedico().getCrm(), em);
        LocalDate dataDisponivel = buscarDataMaisRecente(consultas);

        if((dataDisponivel == novaData || novaData.isAfter(dataDisponivel)) && validaData(novaData)){
            consulta.remarcar(novaData);
            consultaDao.alterar(consulta, em);
            return "Consulta do paciente : "
                    + consulta.getPaciente().getNome()
                    + " com o médico : "
                    + consulta.getMedico().getNome()
                    + " foi remarcada para a data: "
                    + formataData(novaData);

        }else {
            throw new RuntimeException("Data indisponível");
        }


    }

    public List<Consulta> buscarTodasConsultas (EntityManager em) throws ConsultaDataBaseException {
        List<Consulta> consultas = consultaDao.buscarPorConsultas(em);
        return consultas;
    }

    private static String formataData(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR"));
        return data.format(formatter);
    }

    private static LocalDate buscarDataMaisRecente (List<Consulta> consultas){

        if(!consultas.isEmpty()){
            LocalDate dataReferecia = consultas.get(0).getData();

            for (Consulta c : consultas) {
                if(c.getData().isAfter(dataReferecia)){
                    dataReferecia = c.getData();
                }
            }

            LocalDate dataFinal = dataReferecia.plusDays(1);

            while (!validaData(dataFinal))
                dataFinal = dataFinal.plusDays(1);

            return dataFinal;

        }else {
            return LocalDate.now().plusDays(1);
        }
    }

    private static boolean validaData (LocalDate data){
        DayOfWeek dataEmDias = data.getDayOfWeek();

        return dataEmDias != DayOfWeek.SATURDAY && dataEmDias != DayOfWeek.SUNDAY;
    }

    private static void validaOptional (Optional<Object> optional, int op){
        if(optional.isEmpty()){
            if(op == 1)
                throw new RuntimeException("Paciente não encontrado");
            else
                throw new RuntimeException("Medico não encotrado");
        }

    }
}

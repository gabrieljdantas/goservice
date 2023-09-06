package com.soulcode.goserviceapp.repository;

import com.soulcode.goserviceapp.domain.Agendamento;
import com.soulcode.goserviceapp.domain.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    @Query(value = "SELECT a.* FROM agendamentos a " +
            "JOIN usuarios u ON u.id = a.cliente_id " +
            "WHERE (a.data BETWEEN ? AND ?) AND u.email = ?", nativeQuery = true)
    List<Agendamento> findByDataBetweenCliente(LocalDate dataInicio, LocalDate dataFim, String email);

    @Query(value="SELECT a.* FROM agendamentos a JOIN usuarios u ON a.cliente_id = u.id WHERE u.email = ? ORDER BY data", nativeQuery = true)
    List<Agendamento> findByClienteEmail(String email);

    @Query(value = "SELECT a.* FROM agendamentos a JOIN usuarios u ON a.prestador_id = u.id WHERE u.email = ? ORDER BY data", nativeQuery = true)
    List<Agendamento> findByPrestadorEmail(String email);


    @Query(value = "SELECT status_agendamento, COUNT(status_agendamento) FROM agendamentos GROUP BY status_agendamento", nativeQuery = true)
    List<Servico> findByServicosStatus();

}

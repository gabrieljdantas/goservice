package com.soulcode.goserviceapp.repository;

import com.soulcode.goserviceapp.domain.Agendamento;
import com.soulcode.goserviceapp.domain.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByAgendamentoId(Long agendamentoId);
}
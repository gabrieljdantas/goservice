package com.soulcode.goserviceapp.service;

import com.soulcode.goserviceapp.domain.Mensagem;
import com.soulcode.goserviceapp.domain.Agendamento;
import com.soulcode.goserviceapp.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    public MensagemService(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public Mensagem enviarMensagem(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    public List<Mensagem> findByAgendamentoId(Long id) {
        List<Mensagem> list = mensagemRepository.findByAgendamentoId(id);
        return list;
    }

    public Mensagem create(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }
}

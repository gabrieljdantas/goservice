
package com.soulcode.goserviceapp.service;

import com.soulcode.goserviceapp.domain.AgendamentoLog;
import com.soulcode.goserviceapp.repository.AgendamentoLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoLogService {
    private final AgendamentoLogRepository agendamentoLogRepository;

    @Autowired
    public AgendamentoLogService(AgendamentoLogRepository agendamentoLogRepository) {
        this.agendamentoLogRepository = agendamentoLogRepository;
    }
    
    public AgendamentoLog create(AgendamentoLog agendamentoLog){
        return agendamentoLogRepository.save(agendamentoLog);
    }
    public List<AgendamentoLog> findAll() {
        return agendamentoLogRepository.findAll();
    }


}
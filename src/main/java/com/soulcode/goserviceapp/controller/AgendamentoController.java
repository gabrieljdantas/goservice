package com.soulcode.goserviceapp.controller;

import com.soulcode.goserviceapp.domain.Agendamento;
import com.soulcode.goserviceapp.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("/agenda")
    public String agenda(
            @RequestParam("dataInicio") LocalDate dataInicio,
            @RequestParam("dataFim") LocalDate dataFim,
            Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String dataformatada = dataInicio.format(formatter);
        String dataFimFormatada = dataFim.format(formatter);

        List<Agendamento> agendamentos = agendamentoRepository.findByDataBetween(dataInicio, dataFim);

        model.addAttribute("agendamentos", agendamentos);

        return "agendaPrestador";
    }
}


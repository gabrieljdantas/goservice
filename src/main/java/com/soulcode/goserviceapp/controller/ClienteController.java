package com.soulcode.goserviceapp.controller;

import com.soulcode.goserviceapp.domain.*;
import com.soulcode.goserviceapp.domain.enums.StatusAgendamento;
import com.soulcode.goserviceapp.service.*;
import com.soulcode.goserviceapp.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private MensagemService mensagemService;


    @GetMapping(value = "/dados")
    public ModelAndView dados(Authentication authentication) {
        ModelAndView mv = new ModelAndView("dadosCliente");
        try {
            Cliente cliente = clienteService.findAuthenticated(authentication);
            mv.addObject("cliente", cliente);
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException ex) {
            mv.addObject("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao buscar dados do cliente.");
        }
        return mv;
    }

    @PostMapping(value = "/dados")
    public String alterarDados(Cliente cliente, RedirectAttributes attributes) {
        try {
            clienteService.update(cliente);
            attributes.addFlashAttribute("successMessage", "Dados alterados.");
        } catch (UsuarioNaoEncontradoException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao alterar dados cadastrais.");
        }
        return "redirect:/cliente/dados";
    }

    @GetMapping(value = "/agendar")
    public ModelAndView agendar(@RequestParam(name = "especialidade", required = false) Long servicoId) {
        ModelAndView mv = new ModelAndView("agendarServico");
        try {
            List<Servico> servicos = servicoService.findAll();
            mv.addObject("servicos", servicos);
            if(servicoId != null) {
                List<Prestador> prestadores = prestadorService.findByServicoId(servicoId);
                mv.addObject("prestadores", prestadores);
                mv.addObject("servicoId", servicoId);
            }
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao buscar dados de serviços.");
        }
        return mv;
    }

    @PostMapping(value = "/agendar")
    public String criarAgendamento(
            @RequestParam(name = "servicoId") Long servicoId,
            @RequestParam(name = "prestadorId") Long prestadorId,
            @RequestParam(name = "data") LocalDate data,
            @RequestParam(name = "hora") LocalTime hora,
            Authentication authentication,
            RedirectAttributes attributes) {
        try {
            agendamentoService.create(authentication, servicoId, prestadorId, data, hora);
            attributes.addFlashAttribute("successMessage", "Agendamento realizado com sucesso. Aguardando confirmação.");
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException | ServicoNaoEncontradoException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao finalizar agendamento.");
        }
        return "redirect:/cliente/historico";
    }

    @GetMapping(value = "/historico")
    public ModelAndView historico(
            @RequestParam(value = "dataInicio", required = false) LocalDate dataInicio ,
            @RequestParam(value = "dataFim", required = false) LocalDate dataFim,
            Authentication authentication)
             {
        ModelAndView mv = new ModelAndView("historicoCliente");
        if (dataInicio == null) {
            List<Agendamento> agendamentos = agendamentoService.findByCliente(authentication);
            mv.addObject("agendamentos", agendamentos);

            return mv;
        }else {
            try {
                List<Agendamento> agendamentos = agendamentoService.findByDataBetweenCliente(authentication, dataInicio, dataFim);
                mv.addObject("agendamentos", agendamentos);
            }catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException ex) {
                mv.addObject("errorMessage", ex.getMessage());
            }catch (Exception ex) {
                mv.addObject("errorMessage", ex.getMessage());
                ex.printStackTrace();
            }
            return mv;
        }
    }

    @PostMapping(value = "/historico/cancelar")
    public String cancelarAgendamento(
            @RequestParam(name = "agendamentoId") Long agendamentoId,
            Authentication authentication,
            RedirectAttributes attributes) {
        try {
            agendamentoService.cancelAgendaCliente(authentication, agendamentoId);
            attributes.addFlashAttribute("successMessage", "Agendamento cancelado.");
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException |
                 AgendamentoNaoEncontradoException | StatusAgendamentoImutavelException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao cancelar agendamento.");
        }
        return "redirect:/cliente/historico";
    }

    @PostMapping(value = "/historico/concluir")
    public String concluirAgendamento(
            @RequestParam(name = "agendamentoId") Long agendamentoId,
            Authentication authentication,
            RedirectAttributes attributes) {
        try {
            agendamentoService.completeAgenda(authentication, agendamentoId);
            attributes.addFlashAttribute("successMessage", "Agendamento concluído.");
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException |
                 AgendamentoNaoEncontradoException | StatusAgendamentoImutavelException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao concluir agendamento.");
        }
        return "redirect:/cliente/historico";
    }

    @GetMapping("/historico/mensagens/{id}")
    public ModelAndView exibirChat(@PathVariable Long id,
                                   Authentication authentication,
                                   RedirectAttributes attributes) {
        ModelAndView model = new ModelAndView("telaChat");
        Cliente cliente = clienteService.findAuthenticated(authentication);
        List<Mensagem> mensagens = mensagemService.findByAgendamentoId(id);

        Agendamento agendamento = agendamentoService.findById(id);

        if (agendamento != null && agendamento.getCliente().getId().equals(cliente.getId())) {
            if (StatusAgendamento.CONFIRMADO.equals(agendamento.getStatusAgendamento())) {
                model.addObject("agendamento", agendamento);
                model.addObject("mensagens", mensagens);
            } else {
                attributes.addFlashAttribute("errorMessage", "O chat desse agendamento não pode ser acessado!");
                model.setViewName("redirect:/cliente/historico");
            }
        } else {
            attributes.addFlashAttribute("errorMessage", "Você não tem acesso a esse chat de agendamento!");
            model.setViewName("redirect:/cliente/historico");
        }
        return model;
    }

    @PostMapping(value = "/historico/mensagens/enviarMensagem")
    public String enviarMensagem(
            @RequestParam Long agendamentoId,
            @RequestParam String conteudo,
            RedirectAttributes attributes) {
        Agendamento agendamento = agendamentoService.findById(agendamentoId);

        try {
            Mensagem mensagem = new Mensagem();
            mensagem.setAgendamento(agendamento);
            mensagem.setCliente(agendamento.getCliente());
            mensagem.setPrestador(agendamento.getPrestador());
            mensagem.setMensagem(conteudo);
            mensagem.setUsuarioEnvio("cliente");
            mensagem.setDataHoraMensagem(LocalDateTime.now());

            mensagemService.create(mensagem);
            return "redirect:/cliente/historico/mensagens/" + agendamentoId;

        } catch (Exception ex){
            attributes.addFlashAttribute("errorMessage", "Erro ao enviar mensagem");
            return "redirect:/cliente/historico/mensagens/" + agendamentoId;
        }

    }
}

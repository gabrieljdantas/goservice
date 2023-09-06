package com.soulcode.goserviceapp.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name= "mensagem")
public class Mensagem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String mensagem;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataHoraMensagem;

    @ManyToOne
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private Usuario prestador;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @Column( name = "usuario_envio_id",nullable = false, length = 150)
    private String usuarioEnvio;

    public Mensagem() {

    }
    public Mensagem(Long id, String mensagem, LocalDateTime dataHoraMensagem, Agendamento agendamento, Usuario prestador, Usuario cliente, String usuarioEnvio) {
        this.id = id;
        this.mensagem = mensagem;
        this.dataHoraMensagem = dataHoraMensagem;
        this.agendamento = agendamento;
        this.prestador = prestador;
        this.cliente = cliente;
        this.usuarioEnvio = usuarioEnvio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHoraMensagem() {
        return dataHoraMensagem;
    }

    public void setDataHoraMensagem(LocalDateTime dataHoraMensagem) {
        this.dataHoraMensagem = dataHoraMensagem;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Usuario getPrestador() {
        return prestador;
    }

    public void setPrestador(Usuario prestador) {
        this.prestador = prestador;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public String getUsuarioEnvio() {
        return usuarioEnvio;
    }

    public void setUsuarioEnvio(String usuarioEnvio) {
        this.usuarioEnvio = usuarioEnvio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem msg = (Mensagem) o;
        return Objects.equals(id, msg.id) &&
                Objects.equals(mensagem, msg.mensagem) &&
                Objects.equals(dataHoraMensagem, msg.dataHoraMensagem) &&
                Objects.equals(agendamento, msg.agendamento) &&
                Objects.equals(prestador, msg.prestador) &&
                Objects.equals(cliente, msg.cliente) &&
                Objects.equals(usuarioEnvio, msg.usuarioEnvio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mensagem, dataHoraMensagem, agendamento, prestador, cliente, usuarioEnvio );
    }
}

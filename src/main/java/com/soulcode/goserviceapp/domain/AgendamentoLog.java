package com.soulcode.goserviceapp.domain;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.soulcode.goserviceapp.domain.enums.StatusAgendamento;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Document(collection = "agendamentos")
public class AgendamentoLog {

        @Id
        private String id;
        private Long clienteId;
        private Long prestadorId;
        private Long servicoId;
        private LocalDateTime dataHoraRegistro;
        private LocalDate data;
        private LocalTime hora;
        private StatusAgendamento statusAgendamento;

        @Field(targetType = FieldType.DATE_TIME)
        private LocalDateTime dataLog = LocalDateTime.now();


        public AgendamentoLog(Agendamento agendamento){
            this.clienteId =  agendamento.getCliente().getId();
            this.prestadorId = agendamento.getPrestador().getId();
            this.servicoId = agendamento.getServico().getId();
            this.dataHoraRegistro = agendamento.getDataHoraRegistro();
            this.data = agendamento.getData();
            this.hora = agendamento.getHora();
            this.statusAgendamento = agendamento.getStatusAgendamento();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getClienteId() {
            return clienteId;
        }

        public void setClienteId(Long clienteId) {
            this.clienteId = clienteId;
        }

        public Long getPrestadorId() {
            return prestadorId;
        }

        public void setPrestadorId(Long prestadorId) {
            this.prestadorId = prestadorId;
        }

        public Long getServicoId() {
            return servicoId;
        }

        public void setServicoId(Long servicoId) {
            this.servicoId = servicoId;
        }

        public LocalDateTime getDataHoraRegistro() {
            return dataHoraRegistro;
        }

        public void setDataHoraRegistro(LocalDateTime dataHoraRegistro) {
            this.dataHoraRegistro = dataHoraRegistro;
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }

        public LocalTime getHora() {
            return hora;
        }

        public void setHora(LocalTime hora) {
            this.hora = hora;
        }

        public StatusAgendamento getStatusAgendamento() {
            return statusAgendamento;
        }

        public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
            this.statusAgendamento = statusAgendamento;
        }

        public LocalDateTime getDataLog() {
            return dataLog;
        }

        public void setDataLog(LocalDateTime dataLog) {
            this.dataLog = dataLog;
        }
}

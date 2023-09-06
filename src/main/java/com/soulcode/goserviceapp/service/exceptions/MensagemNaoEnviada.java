package com.soulcode.goserviceapp.service.exceptions;

import com.soulcode.goserviceapp.domain.Mensagem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MensagemNaoEnviada extends RuntimeException{

    public MensagemNaoEnviada() {
        super("Senha Incorreta");
    }

    public MensagemNaoEnviada(String message) {
        super(message);
    }
}

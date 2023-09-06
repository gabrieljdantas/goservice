package com.soulcode.goserviceapp.service;

import com.soulcode.goserviceapp.domain.Administrador;
import com.soulcode.goserviceapp.repository.AdministradorRepository;
import com.soulcode.goserviceapp.service.exceptions.UsuarioNaoAutenticadoException;
import com.soulcode.goserviceapp.service.exceptions.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public Administrador findAuthenticated(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()){
            Optional<Administrador> administrador = administradorRepository.findByEmail(authentication.getName());
            if(administrador.isPresent()){
                return administrador.get();
            } else {
                throw new UsuarioNaoEncontradoException();
            }
        } else {
            throw new UsuarioNaoAutenticadoException();
        }
    }

    public Administrador findById(Long id){
        Optional<Administrador> adminstrador = administradorRepository.findById(id);
        if(adminstrador.isPresent()){
            return adminstrador.get();
        } else {
            throw  new UsuarioNaoEncontradoException();
        }
    }

    public Administrador update(Administrador administrador) {
        Administrador updatedAdmin = this.findById(administrador.getId());
        updatedAdmin.setNome(administrador.getNome());
        updatedAdmin.setEmail(administrador.getEmail());
        return administradorRepository.save(updatedAdmin);
    }
}

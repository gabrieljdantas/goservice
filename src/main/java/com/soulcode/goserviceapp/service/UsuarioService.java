package com.soulcode.goserviceapp.service;

import com.soulcode.goserviceapp.domain.Administrador;
import com.soulcode.goserviceapp.domain.Cliente;
import com.soulcode.goserviceapp.domain.Prestador;
import com.soulcode.goserviceapp.domain.Usuario;
import com.soulcode.goserviceapp.repository.UsuarioRepository;
import com.soulcode.goserviceapp.service.exceptions.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    // IoC -> Inversão de Controle
    // DI -> Injeção de Dependência

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Cacheable(cacheNames = "redisCache")
    public Usuario findByEmail(String email){
        System.err.println("BUSCANDO NO BANCO DE DADOS POR EMAIL...");
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()){
            return usuario.get();
        }
        throw new UsuarioNaoEncontradoException();
    }
    @Cacheable(cacheNames = "redisCache")
    public List<Usuario> findAll(){
        System.err.println("BUSCANDO NO BANCO DE DADOS...");
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id){
        Optional<Usuario> result = usuarioRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new UsuarioNaoEncontradoException();
    }

    public Usuario createUser(Usuario usuario){
        String passwordEncoded = encoder.encode(usuario.getSenha());
        usuario.setSenha(passwordEncoded);

        switch (usuario.getPerfil()){
            case PRESTADOR:
                return createAndSavePrestador(usuario);
            case ADMIN:
                return createAndSaveAdministrador(usuario);
            case CLIENTE:
            default:
                return createAndSaveCliente(usuario);
        }
    }

    private Administrador createAndSaveAdministrador(Usuario u){
        Administrador admin = new Administrador(u.getId(), u.getNome(), u.getEmail(), u.getSenha(), u.getPerfil(), u.getHabilitado());
        return usuarioRepository.save(admin);
    }

    private Prestador createAndSavePrestador(Usuario u) {
        Prestador prestador = new Prestador(u.getId(), u.getNome(), u.getEmail(), u.getSenha(), u.getPerfil(), u.getHabilitado());
        return usuarioRepository.save(prestador);
    }

    private Cliente createAndSaveCliente(Usuario u) {
        Cliente cliente = new Cliente(u.getId(), u.getNome(), u.getEmail(), u.getSenha(), u.getPerfil(), u.getHabilitado());
        System.out.println(cliente.getSenha());
        return usuarioRepository.save(cliente);
    }

    @Transactional
    public void disableUser(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()){
            usuarioRepository.updateEnableById(false, id);
            return;
        }
        throw new UsuarioNaoEncontradoException();
    }
    @Transactional
    public void enableUser(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            usuarioRepository.updateEnableById(true, id);
            return;
        }
        throw new UsuarioNaoEncontradoException();
    }


    public List<Usuario> findByFilterName(String nome) {
        List<Usuario> usuarios = usuarioRepository.findByFilterName(nome);

        if (usuarios.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Nenhum usuário encontrado com o nome");
        }
        return usuarios;
    }
}

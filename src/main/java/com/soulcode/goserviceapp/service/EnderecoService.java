package com.soulcode.goserviceapp.service;

import com.soulcode.goserviceapp.domain.Endereco;
import com.soulcode.goserviceapp.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository =  enderecoRepository;
    }

    public Endereco salvarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Endereco obterEnderecoPorId(Long id) {
        return enderecoRepository.findById(id).orElse(null);
    }

    public void excluirEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }

    public void atualizarEndereco(Long id,
                                  String logradouro,
                                  String numero,
                                  String cidade,
                                  String uf) {
        enderecoRepository.atualizarEndereco(id, logradouro, numero, cidade, uf);
    }

}

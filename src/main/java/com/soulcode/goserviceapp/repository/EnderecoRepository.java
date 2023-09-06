package com.soulcode.goserviceapp.repository;

import com.soulcode.goserviceapp.domain.Endereco;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface EnderecoRepository extends JpaRepository<Endereco, Long > {
    @Transactional
    @Modifying
    @Query("UPDATE Endereco e SET e.logradouro = :logradouro, e.numero = :numero, e.cidade = :cidade, e.uf = :uf WHERE e.id = :id")
    void atualizarEndereco(@Param("id") Long id,
                           @Param("logradouro") String logradouro,
                           @Param("numero") String numero,
                           @Param("cidade") String cidade,
                           @Param("uf") String uf);


}




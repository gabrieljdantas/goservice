package com.soulcode.goserviceapp.repository;

import com.soulcode.goserviceapp.domain.Servico;
import com.soulcode.goserviceapp.domain.Usuario;
import com.soulcode.goserviceapp.domain.enums.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    @Query(value =
            "SELECT s.*" +
                    " FROM servicos s" +
                    " JOIN prestadores_servicos ps ON s.id = ps.servico_id" +
                    " JOIN usuarios u ON u.id = ps.prestador_id" +
                    " WHERE u.email = ?", nativeQuery = true)
    List<Servico> findByPrestadorEmail(String email);


    @Query(value = "SELECT * FROM servicos LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<Servico> findAllPaginated(@Param("pageSize") int pageSize, @Param("offset") int offset);


    @Query(value = "SELECT COUNT(*) FROM servicos", nativeQuery = true)
    long countTotalServicos();

    @Query(value = "SELECT * " +
            " FROM servicos" + " WHERE nome LIKE ?%", nativeQuery = true)
    List<Servico> findByFilterService(String nome);
}




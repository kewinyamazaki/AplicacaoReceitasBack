package com.example.sitereceitas.classes.receita.repository;

import com.example.sitereceitas.classes.receita.model.common.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@EnableJpaRepositories
public interface SRReceitasRepository extends JpaRepository<Receita, Long> {

    Page<Receita> findAll(Pageable paginacao);

    @Query(value = "SELECT r AS ingredientes FROM tb_receita r JOIN as_ingrediente_receita a ON r.idreceita=a.idreceita GROUP BY r.idreceita HAVING array_agg(a.idingrediente) <@ ARRAY[?1] ORDER BY COUNT(a);", nativeQuery = true)
    Page<Receita> findByIngredientes(Collection<Integer> idsIngredientes, Pageable paginacao);
}
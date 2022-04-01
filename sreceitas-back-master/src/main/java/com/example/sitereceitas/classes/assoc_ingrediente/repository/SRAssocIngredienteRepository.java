package com.example.sitereceitas.classes.assoc_ingrediente.repository;

import com.example.sitereceitas.classes.assoc_ingrediente.model.common.AssocIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SRAssocIngredienteRepository extends JpaRepository<AssocIngrediente, Long> {

    @Modifying
    @Query("DELETE FROM AssocIngrediente WHERE idreceita = :idreceita")
    void deleteAllByIdReceita(@Param("idreceita") Long id);
}

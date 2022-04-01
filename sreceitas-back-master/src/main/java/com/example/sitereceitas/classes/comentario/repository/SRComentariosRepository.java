package com.example.sitereceitas.classes.comentario.repository;

import com.example.sitereceitas.classes.comentario.model.common.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface SRComentariosRepository extends JpaRepository<Comentario, Long> {

    @Transactional
    @Query(value = "SELECT * FROM tb_comentario WHERE idreceita = :idprocurado ORDER BY idusuario = :idusuario DESC", nativeQuery = true)
    List<Comentario> findByIdReceita(@Param("idprocurado") Long idreceita, @Param("idusuario") Long idusuario);

    @Transactional
    @Query(value = "SELECT * FROM tb_comentario WHERE idreceita = :idprocurado", nativeQuery = true)
    List<Comentario> findByIdReceita(@Param("idprocurado") Long idreceita);
}

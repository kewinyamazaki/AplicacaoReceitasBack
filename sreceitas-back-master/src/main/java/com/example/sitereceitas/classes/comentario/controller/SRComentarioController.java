package com.example.sitereceitas.classes.comentario.controller;

import com.example.sitereceitas.classes.comentario.model.dto.ComentarioDTO;
import com.example.sitereceitas.classes.comentario.service.SRComentarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/v1/comentario")
@AllArgsConstructor
public class SRComentarioController {

    private final SRComentarioService service;

    /* ---------- Endpoints relacionados às Receitas ---------- */

    // Cadastrar
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComentarioDTO> cadastrar(@RequestBody ComentarioDTO dto) {
        return ResponseEntity.ok(service.cadastrar(dto));
    }

    // Listar
    @GetMapping(value="/{idReceita}")
    public ResponseEntity<List<ComentarioDTO>> listar(@PathVariable Long idReceita) {
        return ResponseEntity.ok(service.listar(idReceita));
    }

    // Atualizar
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComentarioDTO> alterar(@RequestBody ComentarioDTO dto) {
        return ResponseEntity.ok(service.alterar(dto));
    }

    // Remover
    @DeleteMapping(value="/{idComentario}")
    public ResponseEntity<Long> deletar(@PathVariable Long idComentario) {
        return ResponseEntity.ok(service.deletar(idComentario));
    }
}

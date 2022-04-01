package com.example.sitereceitas.classes.receita.controller;

import com.example.sitereceitas.classes.receita.model.dto.FiltroDTO;
import com.example.sitereceitas.classes.receita.model.dto.ReceitaDTO;
import com.example.sitereceitas.classes.receita.service.SRReceitaService;
import com.example.sitereceitas.classes.shared.common.PaginacaoDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/v1/receita")
@AllArgsConstructor
public class SRReceitaController {

	private final SRReceitaService service;

	/* ---------- Endpoints relacionados às Receitas ---------- */
	// Cadastrar
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReceitaDTO> cadastrar(@RequestBody ReceitaDTO dto) {
		return ResponseEntity.ok(service.cadastrar(dto));
	}

	// Listar (Pagniação Implícita)
	@PostMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ReceitaDTO>> listar() {
		return ResponseEntity.ok(service.listar(new PaginacaoDTO(10, 0)));
	}

	// Listar (Pagniação Explícita)
	@PostMapping(value = "/listar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ReceitaDTO>> listar(@RequestBody PaginacaoDTO paginacaoDTO){
		return ResponseEntity.ok(service.listar(paginacaoDTO));
	}

	// Filtrar
	@PostMapping(value = "/filtrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ReceitaDTO>> filtrar(@RequestBody FiltroDTO filtroDTO) {
		return ResponseEntity.ok(service.filtrar(filtroDTO.getPaginacao(), filtroDTO.getIngredientes()));
	}

	// Consultar
	@GetMapping(value="/{idReceita}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReceitaDTO> consultar(@PathVariable Long idReceita) {
		return ResponseEntity.ok(service.consultar(idReceita));
	}

	// Alterar
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReceitaDTO> alterar(@RequestBody ReceitaDTO dto) {
		return ResponseEntity.ok(service.alterar(dto));
	}

	// Remover
	@DeleteMapping(value="/{idReceita}")
	public ResponseEntity<Long> delete(@PathVariable Long idReceita) {
		return ResponseEntity.ok(service.deletar(idReceita));
	}

	/* ---------- Endpoints relacionados aos Favoritos ---------- */
	// Favoritar
	@PostMapping(value="/fav", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> cadastrarFavorito(@RequestBody ReceitaDTO idreceita) {
		return ResponseEntity.ok(service.cadastrarFavorito(idreceita.getIdreceita()));
	}

	// Listar
	@PostMapping(value="/fav/listar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ReceitaDTO>> consultarFavorito(@RequestBody PaginacaoDTO paginacaoDTO) {
		return ResponseEntity.ok(service.consultarFavoritos(paginacaoDTO));
	}

	// Remover
	@DeleteMapping(value="/fav/{idreceita}")
	public ResponseEntity<Long> deleteFavorito(@PathVariable Long idreceita) {
		return ResponseEntity.ok(service.deletarFavorito(idreceita));
	}
}

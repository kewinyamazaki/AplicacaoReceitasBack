package com.example.sitereceitas.classes.receita.service;

import com.example.sitereceitas.classes.assoc_ingrediente.model.common.AssocIngrediente;
import com.example.sitereceitas.classes.assoc_ingrediente.model.common.AssocIngredienteKey;
import com.example.sitereceitas.classes.assoc_ingrediente.model.dto.AssocIngredienteDTO;
import com.example.sitereceitas.classes.assoc_ingrediente.repository.SRAssocIngredienteRepository;
import com.example.sitereceitas.classes.auth.service.AuthService;
import com.example.sitereceitas.classes.ingrediente.model.common.Ingrediente;
import com.example.sitereceitas.classes.ingrediente.repository.SRIngredienteRepository;
import com.example.sitereceitas.classes.ingrediente.service.SRIngredienteService;
import com.example.sitereceitas.classes.medida.repository.SRMedidaRepository;
import com.example.sitereceitas.classes.receita.converter.ReceitaMapper;
import com.example.sitereceitas.classes.receita.converter.SRReceitaConverter;
import com.example.sitereceitas.classes.receita.model.common.Receita;
import com.example.sitereceitas.classes.receita.model.dto.ReceitaDTO;
import com.example.sitereceitas.classes.receita.repository.SRReceitasRepository;
import com.example.sitereceitas.classes.shared.common.PaginacaoDTO;
import com.example.sitereceitas.classes.usuario.model.common.Usuario;
import com.example.sitereceitas.classes.usuario.repository.SRUsuarioRepository;
import com.example.sitereceitas.exceptions.NegocioException;
import com.example.sitereceitas.util.MensagensUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SRReceitaService {

	final private SRReceitasRepository receitaRepository;
	final private SRReceitaConverter receitaConverter;
	final private ReceitaMapper receitaMapper;
	final private SRUsuarioRepository usuarioRepository;
	final private SRMedidaRepository medidaRepository;
	final private SRAssocIngredienteRepository assocIngredienteRepository;
	final private SRIngredienteService ingredienteService;
	final private SRIngredienteRepository ingredienteRepository;

	final private AuthService authService;
	final public JdbcTemplate jdbcTemplate;

	/* ---------- Métodos relacionados às Receitas ---------- */

	@Transactional
	public ReceitaDTO cadastrar(ReceitaDTO dto) {
		// Salvar receita
		Receita receita = receitaConverter.DTOtoEntity(dto);
		receita.setUsuario(authService.obterUsuarioAutenticado());
		receita.setVerificada(false);
		receita = receitaRepository.save(receita);
		// Atualizar o conjunto de ingredientes e salvar receita
		receita.setIngredientes(cadastrarAssociacoes(receita, dto.getIngredientes()));
		return receitaConverter.EntityToDTO(receita);
	}

	public Set<AssocIngrediente> cadastrarAssociacoes(Receita receita, AssocIngredienteDTO[] dtos) {
		Set<AssocIngrediente> aiSet = new HashSet<>();
		Long idUnidade = medidaRepository.findByMedida("unidade(s)").orElseThrow(() -> new NegocioException(MensagensUtil.MSG_MEDIDA_INVALIDA)).getIdmedida();
		for (AssocIngredienteDTO assoc : dtos) {
			String ingred; String plural = null;

			// Tratamento de pluralidade
			if (assoc.getMedida().getIdmedida().equals(idUnidade)) {
				if (!assoc.getIngrediente().contains("(")) throw new NegocioException(MensagensUtil.MSG_INGREDIENTE_MAL_INFORMADO);
				plural = assoc.getIngrediente();
				ingred = assoc.getIngrediente().replaceAll("\\(.*?\\)","");
			}
			else ingred = assoc.getIngrediente();

			Ingrediente ingrediente = ingredienteRepository.save(ingredienteService.cadastrarOuObterIngrediente(ingred.toLowerCase()));
			AssocIngrediente ai = new AssocIngrediente(new AssocIngredienteKey(receita.getIdreceita(), ingrediente.getIdingrediente()),
					receita,
					ingrediente,
					plural,
					medidaRepository.findById(assoc.getMedida().getIdmedida()).orElseThrow(() -> new NegocioException(MensagensUtil.MSG_MEDIDA_INVALIDA)),
					assoc.getQuantidade());
			aiSet.add(assocIngredienteRepository.save(ai));
		}
		return aiSet;
	}

	public ReceitaDTO consultar(Long id){
		// Encontrar a receita e retorna-la se presente
		Optional<Receita> receita = receitaRepository.findById(id);
		if(!receita.isPresent())
			throw new NegocioException(MensagensUtil.MSG_RECEITA_INVALIDA);
		return receitaConverter.EntityToDTO(receita.get());
	}

	/* ---------- Método descontinuado por questões de performance futura ---------- */
	//public List<ReceitaDTO> listar(){
	//List<Receita> receitas = receitaRepository.findAll();
	//	if (receitas.isEmpty())
	//		throw new NegocioException(MensagensUtil.MSG_NENHUM_REGISTRO);
	//return receitaConverter.EntityListToDTO(receitas);
	//}

	public Page<ReceitaDTO> listar(PaginacaoDTO paginacaoDTO){
		// Encontrar a página de receitas e retorna-la se não vazia
		Pageable paginacao = PageRequest.of(paginacaoDTO.getNumeroPaginas(), paginacaoDTO.getQuantidadePorPaginas());
		Page<Receita> lista = receitaRepository.findAll(paginacao);
		if(lista.isEmpty()) {
			throw new NegocioException(MensagensUtil.MSG_NENHUM_REGISTRO);
		}
		return new PageImpl<>(receitaConverter.EntityListToDTO(lista.getContent()), paginacao, lista.getTotalElements());
	}

	@Transactional
	public Long deletar(Long id) {
		// Encontrar a receita e remove-la se presente e autorizado
		Optional<Receita> receita = receitaRepository.findById(id);
		if(!receita.isPresent())
			throw new NegocioException(MensagensUtil.MSG_RECEITA_INVALIDA);
		if (!authService.validarAutenticidadeId(receita.get().getUsuario().getUserId()))
			throw new NegocioException(MensagensUtil.MSG_PERMISSAO_NEGADA);
		receitaRepository.delete(receita.get());
		return id;
	}

	@Transactional
	public ReceitaDTO alterar(ReceitaDTO dto){
		// Encontrar a receita
		Receita receita = receitaRepository.findById(dto.getIdreceita())
				.orElseThrow(() -> new NegocioException(MensagensUtil.MSG_RECEITA_INVALIDA));
		// Validar a autoria do usuário
		Usuario usuario = receita.getUsuario();
		if (!authService.validarAutenticidadeId(usuario.getUserId()))
			throw new NegocioException(MensagensUtil.MSG_PERMISSAO_NEGADA);
		// Alterar a receita no banco
		dto.setNome_usuario(authService.obterUsuarioAutenticado().getNome());
		System.out.println(dto.getVerificada());
		receita = receitaRepository.save(receitaConverter.DTOtoEntity(dto));
		receita.setUsuario(usuario);
		// TO-DO: Ajustar o lógica para o novo sistema de associação de ingredientes
		assocIngredienteRepository.deleteAllByIdReceita(receita.getIdreceita());
		receita.setIngredientes(cadastrarAssociacoes(receita, dto.getIngredientes()));
		return receitaConverter.EntityToDTO(receita);
	}

	public Page<ReceitaDTO> filtrar(PaginacaoDTO paginacaoDTO, String[] ingredientes){
		// Encontrar o ID de cada ingrediente informado
		Pageable paginacao = PageRequest.of(paginacaoDTO.getNumeroPaginas(), paginacaoDTO.getQuantidadePorPaginas());
		List<Integer> arr = new ArrayList<>();
		for (String i: ingredientes)
			ingredienteRepository.findByNome(i).ifPresent(e -> arr.add(Math.toIntExact(e.getIdingrediente())));
		if (arr.isEmpty())
			throw new NegocioException(MensagensUtil.MSG_NENHUM_INGREDIENTE);
		// Junta os IDs e procura no banco
		String queryArray = arr.stream().map(Object::toString).collect(Collectors.joining(","));
		List<Receita> lista = jdbcTemplate.query(
				"SELECT r.* FROM tb_receita r JOIN as_ingrediente_receita a ON r.idreceita=a.idreceita " +
					"GROUP BY r.idreceita HAVING array_agg(a.idingrediente) @> ARRAY[" + queryArray + "] ORDER BY COUNT(a) " +
					"LIMIT " + paginacao.getPageSize() + " OFFSET " + paginacao.getOffset(),
				receitaMapper
		);
		// Page<Receita> lista = receitaRepository.findByIngredientes(arr, paginacao);
		return new PageImpl<>(receitaConverter.EntityListToDTO(lista), paginacao, lista.size());
	}

	/* ---------- Métodos relacionados aos Favoritos ---------- */

	@Transactional
	public Long cadastrarFavorito(Long idreceita){
		Usuario usuario = authService.obterUsuarioAutenticado();
		usuario.getFavoritos().add(receitaRepository.findById(idreceita)
				.orElseThrow(() -> new NegocioException(MensagensUtil.MSG_RECEITA_INVALIDA)));
		usuarioRepository.save(usuario);
		return idreceita;
	}

	@Transactional
	public Page<ReceitaDTO> consultarFavoritos(PaginacaoDTO paginacaoDTO){
		Pageable paginacao = PageRequest.of(paginacaoDTO.getNumeroPaginas(), paginacaoDTO.getQuantidadePorPaginas());
		Usuario usuario = authService.obterUsuarioAutenticado();
		List<ReceitaDTO> favoritos = receitaConverter.EntityListToDTO(usuario.getFavoritos().stream()
				.skip((long) paginacao.getPageNumber() * paginacao.getPageSize())
				.limit(paginacao.getPageSize())
				.collect(toList())
		);
		if(favoritos.isEmpty())
			throw new NegocioException(MensagensUtil.MSG_FAVORITO_INVALIDO);
		return new PageImpl<>(favoritos, paginacao, usuario.getFavoritos().size());
	}

	@Transactional
	public Long deletarFavorito(Long idreceita) {
		// Remover a receita informada dos favoritos se for uma requisição válida
		Usuario usuario = authService.obterUsuarioAutenticado();
		usuario.getFavoritos().remove(receitaRepository.findById(idreceita)
				.orElseThrow(() -> new NegocioException(MensagensUtil.MSG_RECEITA_INVALIDA)));
		usuarioRepository.save(usuario);
		return idreceita;
	}
}

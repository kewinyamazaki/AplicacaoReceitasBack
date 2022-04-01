package com.example.sitereceitas.classes.comentario.service;

import com.example.sitereceitas.classes.auth.service.AuthService;
import com.example.sitereceitas.classes.comentario.converter.SRComentarioConverter;
import com.example.sitereceitas.classes.comentario.model.common.Comentario;
import com.example.sitereceitas.classes.comentario.model.dto.ComentarioDTO;
import com.example.sitereceitas.classes.comentario.repository.SRComentariosRepository;
import com.example.sitereceitas.exceptions.NegocioException;
import com.example.sitereceitas.util.MensagensUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SRComentarioService {

    private final SRComentarioConverter converter;
    private final SRComentariosRepository repository;
    private final AuthService authService;

    @Transactional
    public ComentarioDTO cadastrar(ComentarioDTO dto) {
        Comentario comentario = converter.DTOtoEntity(dto);
        comentario.setUsuario(authService.obterUsuarioAutenticado());
        comentario.setTimestamp(Date.from(Instant.now()));
        ComentarioDTO retorno = converter.EntityToDTO(repository.save(comentario));
        retorno.setLogadoComoAutor(true);
        return retorno;
    }

    @Transactional
    public List<ComentarioDTO> listar(Long idReceita) {
        List<Comentario> comentarios = authService.autenticado() ?
                repository.findByIdReceita(idReceita, authService.obterUsuarioAutenticado().getUserId()):
                repository.findByIdReceita(idReceita);
        // if (comentarios.isEmpty())
        //     throw new NegocioException(MensagensUtil.MSG_NENHUM_REGISTRO);
        return converter.EntityListToDTO(comentarios);
    }

    @Transactional
    public ComentarioDTO alterar(ComentarioDTO dto) {
        Comentario comentario = repository.findById(dto.getIdcomentario())
                .orElseThrow(() -> new NegocioException(MensagensUtil.MSG_COMENTARIO_INVALIDO));
        if (!authService.validarAutenticidadeId(comentario.getUsuario().getUserId()))
            throw new NegocioException(MensagensUtil.MSG_PERMISSAO_NEGADA);
        comentario.setConteudo(dto.getConteudo());
        repository.save(comentario);
        return converter.EntityToDTO(comentario);
    }

    @Transactional
    public Long deletar(Long id) {
        Optional<Comentario> comentario = repository.findById(id);
        if (!comentario.isPresent())
            throw new NegocioException(MensagensUtil.MSG_COMENTARIO_INVALIDO);
        if (!authService.validarAutenticidadeId(comentario.get().getUsuario().getUserId()))
            throw new NegocioException(MensagensUtil.MSG_PERMISSAO_NEGADA);
        repository.delete(comentario.get());
        return id;
    }
}

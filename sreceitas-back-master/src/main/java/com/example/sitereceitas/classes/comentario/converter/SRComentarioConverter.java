package com.example.sitereceitas.classes.comentario.converter;

import com.example.sitereceitas.classes.auth.service.AuthService;
import com.example.sitereceitas.classes.comentario.model.common.Comentario;
import com.example.sitereceitas.classes.comentario.model.dto.ComentarioDTO;
import com.example.sitereceitas.classes.receita.repository.SRReceitasRepository;
import com.example.sitereceitas.exceptions.NegocioException;
import com.example.sitereceitas.util.MensagensUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SRComentarioConverter {

    private final AuthService authService;
    private final SRReceitasRepository receitasRepository;

    public Comentario DTOtoEntity(ComentarioDTO dto) {
        Comentario comentario = new Comentario();
        comentario.setReceita(receitasRepository.findById(dto.getReceita().getIdreceita())
                .orElseThrow(() -> new NegocioException(MensagensUtil.MSG_RECEITA_INVALIDA)));
        comentario.setConteudo(dto.getConteudo());
        comentario.setTimestamp(dto.getTimestamp());
        return comentario;
    }

    public ComentarioDTO EntityToDTO(Comentario c) {
        ComentarioDTO comentario = new ComentarioDTO();
        comentario.setIdcomentario(c.getIdcomentario());
        comentario.setNome_usuario(c.getUsuario().getNome());
        comentario.setConteudo(c.getConteudo());
        comentario.setTimestamp(c.getTimestamp());
        return comentario;
    }

    public List<ComentarioDTO> EntityListToDTO(List<Comentario> comentarios) {
        ArrayList<ComentarioDTO> listaDto = new ArrayList<>();
        for (Comentario comentario: comentarios) {
            ComentarioDTO c = EntityToDTO(comentario);
            try {c.setLogadoComoAutor(authService.validarAutenticidadeId(comentario.getUsuario().getUserId()));}
            catch (ClassCastException e) {}
            listaDto.add(c);
        }
        return listaDto;
    }
}

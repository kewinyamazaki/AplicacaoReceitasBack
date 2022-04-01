package com.example.sitereceitas.classes.receita.converter;

import com.example.sitereceitas.classes.assoc_ingrediente.converter.SRAssocIngredienteConverter;
import com.example.sitereceitas.classes.auth.service.AuthService;
import com.example.sitereceitas.classes.receita.model.common.Receita;
import com.example.sitereceitas.classes.receita.model.dto.ReceitaDTO;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SRReceitaConverter {

    final private AuthService authService;
    final private SRAssocIngredienteConverter assocIngredienteConverter;

    public ReceitaDTO EntityToDTO(Receita receita){
        ReceitaDTO receitaDTO = new ReceitaDTO();
        receitaDTO.setIdreceita(receita.getIdreceita());
        receitaDTO.setNome_usuario(receita.getUsuario().getNome());
        receitaDTO.setVerificada(receita.getVerificada());
        receitaDTO.setNome(receita.getNome());
        receitaDTO.setImagem(Base64.encodeBase64String(receita.getImagem()));
        receitaDTO.setDescricao(receita.getDescricao());
        receitaDTO.setModo_preparo(receita.getModo_preparo());
        receitaDTO.setTempo_preparo(receita.getTempo_preparo());
        receitaDTO.setRendimento(receita.getRendimento());
        receitaDTO.setIngredientes(assocIngredienteConverter.EntitySetToDTOList(receita.getIngredientes()));
        receitaDTO.setInformacoes_adicionais(receita.getInformacoes_adicionais());
        if (authService.autenticado()) {
            receitaDTO.setFavorito(authService.obterUsuarioAutenticado().getFavoritos().contains(receita));
            receitaDTO.setLogadoComoAutor(authService.validarAutenticidadeId(receita.getUsuario().getUserId()));
        }
        return receitaDTO;
    }

    public Receita DTOtoEntity(ReceitaDTO dto) {
        Receita receita = new Receita();
        receita.setIdreceita(dto.getIdreceita());
        receita.setVerificada(dto.getVerificada());
        receita.setNome(dto.getNome());
        receita.setDescricao(dto.getDescricao());
        if (dto.getImagem() != null)
            receita.setImagem(Base64.decodeBase64(dto.getImagem()));
        receita.setModo_preparo(dto.getModo_preparo());
        receita.setTempo_preparo(dto.getTempo_preparo());
        receita.setRendimento(dto.getRendimento());
        receita.setInformacoes_adicionais(dto.getInformacoes_adicionais());
        return receita;
    }

    public List<ReceitaDTO> EntityListToDTO(List<Receita> lista){
        ArrayList<ReceitaDTO> listaDto = new ArrayList<ReceitaDTO>();
        for (Receita receita: lista)
            listaDto.add(EntityToDTO(receita));
        return listaDto;
    }
}

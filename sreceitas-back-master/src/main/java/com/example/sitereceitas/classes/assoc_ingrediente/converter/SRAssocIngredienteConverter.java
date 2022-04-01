package com.example.sitereceitas.classes.assoc_ingrediente.converter;

import com.example.sitereceitas.classes.assoc_ingrediente.model.common.AssocIngrediente;
import com.example.sitereceitas.classes.assoc_ingrediente.model.dto.AssocIngredienteDTO;
import com.example.sitereceitas.classes.medida.repository.SRMedidaRepository;
import com.example.sitereceitas.exceptions.NegocioException;
import com.example.sitereceitas.util.MensagensUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class SRAssocIngredienteConverter {

    final private SRMedidaRepository medidaRepository;

    private AssocIngredienteDTO EntityToDTO(AssocIngrediente assoc) {
        AssocIngredienteDTO dto = new AssocIngredienteDTO();
        dto.setQuantidade(assoc.getQuantidade());
        dto.setMedida(medidaRepository.findById(assoc.getMedida().getIdmedida()).orElseThrow(() -> new NegocioException(MensagensUtil.MSG_MEDIDA_INVALIDA)));
        dto.setIngrediente(assoc.getIngrediente().getNome());
        dto.setPlural(assoc.getPlural());
        return dto;
    }

    public AssocIngredienteDTO[] EntitySetToDTOList(Set<AssocIngrediente> associacoes) {
        AssocIngredienteDTO[] arr = new AssocIngredienteDTO[associacoes.size()];
        int i = 0;
        for (AssocIngrediente ingrediente: associacoes)
            arr[i++] = EntityToDTO(ingrediente);
        return arr;
    }
}

package com.example.sitereceitas.classes.receita.converter;

import com.example.sitereceitas.classes.assoc_ingrediente.model.common.AssocIngrediente;
import com.example.sitereceitas.classes.assoc_ingrediente.model.common.AssocIngredienteKey;
import com.example.sitereceitas.classes.ingrediente.repository.SRIngredienteRepository;
import com.example.sitereceitas.classes.medida.repository.SRMedidaRepository;
import com.example.sitereceitas.classes.receita.repository.SRReceitasRepository;
import com.example.sitereceitas.exceptions.NegocioException;
import com.example.sitereceitas.util.MensagensUtil;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class AssociacaoMapper implements RowMapper<AssocIngrediente> {

    private SRMedidaRepository medidaRepository;
    private SRReceitasRepository receitasRepository;
    private SRIngredienteRepository ingredienteRepository;

    @Override
    public AssocIngrediente mapRow(ResultSet rs, int rowNum) throws SQLException {
        AssocIngrediente assoc = new AssocIngrediente();
        assoc.setId(new AssocIngredienteKey(rs.getLong("idreceita"), rs.getLong("idingrediente")));
        // Receita
        assoc.setReceita(receitasRepository.findById(rs.getLong("idreceita")).orElseThrow(() -> new NegocioException(MensagensUtil.MSG_RECEITA_INVALIDA)));
        // Ingrediente
        assoc.setIngrediente(ingredienteRepository.findById(rs.getLong("idingrediente")).orElseThrow(() -> new NegocioException(MensagensUtil.MSG_NENHUM_INGREDIENTE)));
        // Medida
        assoc.setMedida(medidaRepository.findById(rs.getLong("idmedida")).orElseThrow(() -> new NegocioException(MensagensUtil.MSG_MEDIDA_INVALIDA)));
        assoc.setQuantidade(rs.getLong("quantidade"));
        if (rs.getString("plural") != null)
            assoc.setPlural(rs.getString("plural"));
        return assoc;
    }
}

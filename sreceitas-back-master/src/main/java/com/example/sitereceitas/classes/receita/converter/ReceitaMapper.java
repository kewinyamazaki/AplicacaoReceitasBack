package com.example.sitereceitas.classes.receita.converter;

import com.example.sitereceitas.classes.assoc_ingrediente.model.common.AssocIngrediente;
import com.example.sitereceitas.classes.receita.model.common.Receita;
import com.example.sitereceitas.classes.usuario.repository.SRUsuarioRepository;
import com.example.sitereceitas.exceptions.NegocioException;
import com.example.sitereceitas.util.MensagensUtil;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Component
@AllArgsConstructor
public class ReceitaMapper implements RowMapper {

    final private SRUsuarioRepository usuarioRepository;
    final private JdbcTemplate jdbcTemplate;
    final private AssociacaoMapper associacaoMapper;

    @Override
    public Receita mapRow(ResultSet rs, int rowNum) throws SQLException {
        Receita receita = new Receita();
        // Usuário
        receita.setUsuario(usuarioRepository.findById(rs.getLong("idusuario")).orElseThrow(() -> new NegocioException(MensagensUtil.MSG_USUARIO_INVALIDO)));
        // Dados
        receita.setIdreceita(rs.getLong("idreceita"));
        receita.setVerificada(rs.getBoolean("verificada"));
        receita.setNome(rs.getString("nome"));
        receita.setImagem(rs.getBytes("imagem"));
        receita.setDescricao(rs.getString("descricao"));
        receita.setModo_preparo(rs.getString("modo_preparo"));
        receita.setTempo_preparo(rs.getInt("tempo_preparo"));
        receita.setRendimento(rs.getInt("rendimento"));
        receita.setInformacoes_adicionais(rs.getString("informacoes_adicionais"));
        // Ingredientes
        List<AssocIngrediente> arr = jdbcTemplate.query("SELECT a.* FROM as_ingrediente_receita a WHERE idreceita = " + receita.getIdreceita(), associacaoMapper);
        if (!arr.isEmpty())
            receita.setIngredientes(new HashSet<>(arr));
        return receita;
    }
}
